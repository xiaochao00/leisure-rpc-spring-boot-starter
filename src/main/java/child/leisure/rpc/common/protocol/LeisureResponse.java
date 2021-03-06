package child.leisure.rpc.common.protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息响应类
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 16:11
 */
public class LeisureResponse implements Serializable {
    private static final long serialVersionUID = 3771990416961654613L;
    private LeisureStatus status;
    private Map<String, String> headers = new HashMap<>();
    private Object returnValue;
    private Exception exception;

    public LeisureResponse(LeisureStatus status) {
        this.status = status;
    }

    public LeisureStatus getStatus() {
        return status;
    }

    public void setStatus(LeisureStatus status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(String key, String value) {
        this.headers.put(key, value);
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "LeisureResponse{" +
                "status=" + status +
                ", headers=" + headers +
                ", returnValue=" + returnValue +
                ", exception=" + exception +
                '}';
    }
}
