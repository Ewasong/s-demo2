package top.sorie.rpc.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import top.sorie.rpc.contant.ZkPropertiesConstant;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CuratorUtils {
    private static final int BASE_SLEEP_TIME = 1000;
    private static final int MAX_RETRIES = 3;

    private static volatile CuratorFramework zkClient;

    public static CuratorFramework getZkClient() {
        if (zkClient != null && zkClient.getState() == CuratorFrameworkState.STARTED) {
            return zkClient;
        }
        synchronized(CuratorUtils.class) {
            // Retry strategy. Retry 3 times, and will increase the sleep time between retries.
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
            zkClient = CuratorFrameworkFactory.builder()
                    // the server to connect to (can be a server list)
                    .connectString(ZkPropertiesConstant.getZookeeperAddress())
                    .retryPolicy(retryPolicy)
                    .build();
            zkClient.start();
        }
        try {
            // wait 30s until connect to the zookeeper
            if (!zkClient.blockUntilConnected(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("Time out waiting to connect to ZK!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zkClient;
    }
}
