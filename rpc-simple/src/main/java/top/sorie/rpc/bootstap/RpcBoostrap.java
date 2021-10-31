package top.sorie.rpc.bootstap;

import top.sorie.rpc.config.ConfigReader;
import top.sorie.rpc.contant.ZkPropertiesConstant;
import top.sorie.rpc.spi.ExtensionLoader;
import top.sorie.rpc.utils.CuratorUtils;

public class RpcBoostrap {
    public void start() {
        // 读取config
        initConfig();
        // 连接zk
        initRpcClient();
        // 注册Service

        // 开启netty
        // 更新Service状态


    }

    private void initRpcClient() {
        CuratorUtils.getZkClient();
    }
    private void initConfig() {
        ConfigReader configReader = ExtensionLoader.getExtensionLoader(ConfigReader.class)
                .getExtension("default");
        configReader.init();
    }
}
