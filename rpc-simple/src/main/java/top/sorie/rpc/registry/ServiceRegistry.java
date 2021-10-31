package top.sorie.rpc.registry;

import top.sorie.rpc.spi.SPI;

@SPI
public interface Registry {
    public void refresh();
}
