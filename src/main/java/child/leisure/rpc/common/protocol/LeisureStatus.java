package child.leisure.rpc.common.protocol;

import java.io.Serializable;

/**
 * 请求响应状态码类
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 16:12
 */
public enum LeisureStatus  {
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "ERROR"),
    NOT_FOUNT(404, "NOT ERROR");

    private int code;
    private String msg;

    LeisureStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
