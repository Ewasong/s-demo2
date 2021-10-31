package top.sorie.proxy.jdk;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceImpl implements HelloService {
    @Override
    public void hello(String name) {
        log.info("hello, {}", name);
    }
}
