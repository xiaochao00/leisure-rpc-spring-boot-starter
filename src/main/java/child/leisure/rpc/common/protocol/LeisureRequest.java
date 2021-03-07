package child.leisure.rpc.common.protocol;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求消息封装类
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 16:00
 */
public class LeisureRequest implements Serializable {

    private static final long serialVersionUID = -3004346757796573668L;

    private String serviceName;

    private String method;

    private Map<String, String> headers = new HashMap<>();

    private Class<?>[] parameterTypes;

    private Object[] parameters;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String name) {
        return this.headers == null ? null : this.headers.get(name);
    }

    @Override
    public String toString() {
        return "LeisureRequest{" +
                "serviceName='" + serviceName + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
