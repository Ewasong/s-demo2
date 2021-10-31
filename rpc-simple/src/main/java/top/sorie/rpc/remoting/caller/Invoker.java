package top.sorie.rpc.remoting.caller;

public interface Invoker {

    public Object invoke(String serviceName, String methodName,
                         Object[] args, Class<?> argTypes);
}
