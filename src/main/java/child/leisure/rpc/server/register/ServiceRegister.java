package child.leisure.rpc.server.register;

import java.net.UnknownHostException;

public interface ServiceRegister {
    /**
     * 注册具体的服务
     * 将服务对象缓存起来，在客户端调用服务时，通过缓存的ServiceObject对象反射指定服务，调用方法。
     *
     * @param serviceObject 持有服务的对象
     */
    void register(ServiceObject serviceObject) throws Exception;

    /**
     * 得到指定服务的服务提供对象
     *
     * @param name 服务名
     * @return 服务持有对象
     */
    ServiceObject getServerObject(String name);
}
