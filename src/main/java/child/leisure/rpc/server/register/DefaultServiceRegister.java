package child.leisure.rpc.server.register;

import child.leisure.rpc.exception.LeisureException;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的服务注册器
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 17:16
 */
public class DefaultServiceRegister implements ServiceRegister {
    private Map<String, ServiceObject> serviceMap = new HashMap<>();
    protected String protocol;
    protected Integer port;

    @Override
    public void register(ServiceObject serviceObject) throws Exception {
        if (serviceObject == null) {
            throw new LeisureException("Parameter can not be empty.");
        }
        this.serviceMap.put(serviceObject.getName(), serviceObject);
    }

    @Override
    public ServiceObject getServerObject(String name) {
        return this.serviceMap.get(name);
    }
}
