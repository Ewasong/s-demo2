package top.sorie.proxy.jdk;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
@AllArgsConstructor
public class JdkHandler implements InvocationHandler {
    private final Object target;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("hand before");
        method.invoke(target, args);
        log.info("hand after");
        return null;
    }

}
