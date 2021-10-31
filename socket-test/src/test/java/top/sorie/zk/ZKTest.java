package top.sorie.zk;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ZKTest {
    private static final int BASE_SLEEP_TIME = 1000;
    private static final int MAX_RETRIES = 3;

    @Test
    public void connect() {
        // Retry strategy. Retry 3 times, and will increase the sleep time between retries.
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                // the server to connect to (can be a server list)
                .connectString("192.168.1.200:2181,192.168.1.200:2182,192.168.1.200:2183")
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
    }

    public CuratorFramework getClient() {
        // Retry strategy. Retry 3 times, and will increase the sleep time between retries.
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                // the server to connect to (can be a server list)
                .connectString("192.168.1.200:2181,192.168.1.200:2182,192.168.1.200:2183")
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }

    @Test
    public void create() throws Exception {
        CuratorFramework zkClient = getClient();
        zkClient.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT).forPath("/node1/00001");
        System.out.println();
        zkClient.close();
    }

    @Test
    public void createEPHEMERAL() throws Exception {
        CuratorFramework zkClient = getClient();
        zkClient.create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL).forPath("/node1/00003");
        System.out.println();
        zkClient.close();
    }
    @Test
    public void exist() throws Exception {
        CuratorFramework zkClient = getClient();
        //不为null的话，说明节点创建成功
        System.out.println(zkClient.checkExists().forPath("/node1/00001"));
    }
    @Test
    public void getData() throws Exception {
        CuratorFramework zkClient = getClient();
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/node1/00002","java".getBytes());
        System.out.println(new String(zkClient.getData().forPath("/node1/00002")));
        zkClient.close();
    }

    @Test
    public void delete() throws Exception {
        CuratorFramework zkClient = getClient();
        zkClient.delete().forPath("/node1/00001");
        zkClient.close();
    }
    @Test
    public void deleteAll() throws Exception {
        CuratorFramework zkClient = getClient();
        zkClient.delete().deletingChildrenIfNeeded().forPath("/node1");
        zkClient.close();
    }
    @Test
    public void update() throws Exception {
        CuratorFramework zkClient = getClient();
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath("/node1/00001","java".getBytes());
        zkClient.getData().forPath("/node1/00001");//获取节点的数据内容
        zkClient.setData().forPath("/node1/00001","c++".getBytes());//更新节点数据内容
        zkClient.close();
    }
    @Test
    public void getAllPath() throws Exception{
        CuratorFramework zkClient = getClient();
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath("/node1/00001","java".getBytes());
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath("/node1/00002","java".getBytes());
        List<String> childrenPaths = zkClient.getChildren().forPath("/node1");
        System.out.println(childrenPaths);
        zkClient.close();
    }

    @Test
    public void listen() throws Exception{
        CuratorFramework zkClient = getClient();
        String path = "/node1";
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath("/node1/00001","java".getBytes());
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            System.out.println("aaa");
            System.out.println(pathChildrenCacheEvent.getType());
            // do something
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start();
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath("/node1/00002","java".getBytes());
        Thread.sleep(10000);
        zkClient.close();
        pathChildrenCache.close();
    }
}
