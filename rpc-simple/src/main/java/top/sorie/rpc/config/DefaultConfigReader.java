package top.sorie.rpc.config;

import top.sorie.rpc.contant.ZkPropertiesConstant;

public class DefaultConfigReader implements ConfigReader {
    @Override
    public void init() {
        ZkPropertiesConstant.init();
    }
}
