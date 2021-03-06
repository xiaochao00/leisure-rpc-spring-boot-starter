package child.leisure.rpc.server.net.handler;

import child.leisure.rpc.common.protocol.LeisureRequest;
import child.leisure.rpc.common.protocol.LeisureResponse;
import child.leisure.rpc.common.protocol.LeisureStatus;
import child.leisure.rpc.common.protocol.MessageProtocol;
import child.leisure.rpc.server.register.ServiceRegister;
import child.leisure.rpc.server.register.ServiceObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * RPC请求处理器，提供解组请求，编组响应的操作
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 17:55
 */
public class RequestHandler {
    private MessageProtocol protocol;

    private ServiceRegister serviceRegister;

    public RequestHandler(MessageProtocol protocol, ServiceRegister serviceRegister) {
        super();
        this.protocol = protocol;
        this.serviceRegister = serviceRegister;
    }

    public byte[] handleRequest(byte[] data) throws Exception {
        // 1.解组请求体
        LeisureRequest request = this.protocol.unmarshallingRequest(data);
        // 2.查找服务对象
        ServiceObject serviceObject = this.serviceRegister.getServerObject(request.getServiceName());
        // 3.反射调用响应方法
        LeisureResponse response;
        if (serviceObject == null) {
            response = new LeisureResponse(LeisureStatus.NOT_FOUNT);
        } else {
            try {
                Method method = serviceObject.getClazz().getMethod(request.getMethod(), request.getParameterTypes());
                Object returnValue = method.invoke(serviceObject.getObj(), request.getParameters());
                response = new LeisureResponse(LeisureStatus.SUCCESS);
                response.setReturnValue(returnValue);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                response = new LeisureResponse(LeisureStatus.ERROR);
                response.setException(e);
            }
        }
        // 4.编组响应体
        return this.protocol.marshallingResponse(response);
    }

    public MessageProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(MessageProtocol protocol) {
        this.protocol = protocol;
    }

    public ServiceRegister getServerRegister() {
        return serviceRegister;
    }

    public void setServerRegister(ServiceRegister serviceRegister) {
        this.serviceRegister = serviceRegister;
    }
}
