package top.sorie.rpc.contant;

import lombok.Getter;
import top.sorie.rpc.utils.PropertiesFileUtils;

import java.util.Properties;


public class ZkPropertiesConstant {
    private static final String DEFAULT_ZOOKEEPER_ADDRESS = "127.0.0.1:2181";

    public static final String CONFIG_FILE = "rpc.properties";
    public static final String ZK_ADDRESS = "rpc.zookeeper.address";
    private static volatile String zookeeperAddress;
    public static void init() {
        Properties properties = PropertiesFileUtils.readProperitesFile(ZkPropertiesConstant.CONFIG_FILE);
        zookeeperAddress = properties != null && properties
                .getProperty(ZkPropertiesConstant.ZK_ADDRESS) != null
                ? properties.getProperty(ZkPropertiesConstant.ZK_ADDRESS) : DEFAULT_ZOOKEEPER_ADDRESS;
    }
    public static String getZookeeperAddress() {
        return zookeeperAddress;
    }
}
