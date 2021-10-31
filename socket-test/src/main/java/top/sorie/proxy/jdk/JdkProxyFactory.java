package top.sorie.proxy.jdk;

import java.lang.reflect.Proxy;

public class JdkProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new JdkHandler(target));
    }

    public static void main(String[] args) {
        HelloService helloService = (HelloService)
                JdkProxyFactory.getProxy(new HelloServiceImpl());
        helloService.hello("sorie");
    }
}
