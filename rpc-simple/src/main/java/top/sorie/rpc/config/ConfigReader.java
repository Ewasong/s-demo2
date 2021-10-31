package top.sorie.rpc.config;

import top.sorie.rpc.spi.SPI;

@SPI
public interface ConfigReader {
    public void init();
}
