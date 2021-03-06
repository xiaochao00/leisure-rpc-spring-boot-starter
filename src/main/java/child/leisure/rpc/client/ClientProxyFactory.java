package child.leisure.rpc.client;

import child.leisure.rpc.client.discovery.ServiceDiscoverer;
import child.leisure.rpc.client.net.NetClient;
import child.leisure.rpc.common.protocol.LeisureRequest;
import child.leisure.rpc.common.protocol.LeisureResponse;
import child.leisure.rpc.common.protocol.MessageProtocol;
import child.leisure.rpc.common.service.Service;
import child.leisure.rpc.exception.LeisureException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * 客户端代理工厂：用于创建远程服务代理类
 * 封装编组请求、请求发送、编组响应等操作
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 15:06
 */
public class ClientProxyFactory {

    private ServiceDiscoverer serviceDiscoverer;

    private Map<String, MessageProtocol> supportMessageProtocols;

    private NetClient netClient;

    private Map<Class<?>, Object> objectMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) this.objectMap.computeIfAbsent(clazz, cls -> newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, new ClientInvocationHandler(cls)));
    }

    private class ClientInvocationHandler implements InvocationHandler {
        private Class<?> clazz;

        private Random random = new Random();

        ClientInvocationHandler(Class<?> clazz) {
            super();
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
            if ("toString".equals(method.getName())) {
                return proxy.getClass().toString();
            }

            if ("hashCode".equals(method.getName())) {
                return 0;
            }
            // 获取服务信息
            String serviceName = this.clazz.getName();
            List<Service> serviceList = serviceDiscoverer.getServices(serviceName);
            if (serviceList == null || serviceList.isEmpty()) {
                throw new LeisureException("Here is no provider.");
            }
            // 1.随机选择一个服务提供者
            Service service = serviceList.get(random.nextInt(serviceList.size()));
            // 2.构造request对象
            LeisureRequest request = new LeisureRequest();
            request.setServiceName(serviceName);
            request.setMethod(method.getName());
            request.setParameterTypes(method.getParameterTypes());
            request.setParameters(args);
            // 3.协议层编组，获得协议对象
            MessageProtocol protocol = supportMessageProtocols.get(service.getProtocol());
            // 3.2.编组
            byte[] data = protocol.marshallingRequest(request);
            // 4.调用网络层发送请求
            byte[] responseData = netClient.sendRequest(data, service);
            // 5.解组响应对象
            LeisureResponse response = protocol.unmarshallingResponse(responseData);
            // 6.结果处理
            if (response.getException() != null) {
                throw response.getException();
            }
            return response.getReturnValue();
        }
    }

    public ServiceDiscoverer getServiceDiscoverer() {
        return serviceDiscoverer;
    }

    public void setServiceDiscoverer(ServiceDiscoverer serviceDiscoverer) {
        this.serviceDiscoverer = serviceDiscoverer;
    }

    public Map<String, MessageProtocol> getSupportMessageProtocols() {
        return supportMessageProtocols;
    }

    public void setSupportMessageProtocols(Map<String, MessageProtocol> supportMessageProtocols) {
        this.supportMessageProtocols = supportMessageProtocols;
    }

    public NetClient getNetClient() {
        return netClient;
    }

    public void setNetClient(NetClient netClient) {
        this.netClient = netClient;
    }

    public Map<Class<?>, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<Class<?>, Object> objectMap) {
        this.objectMap = objectMap;
    }
}
