package child.leisure.rpc.common.protocol;

import java.io.IOException;

/**
 * 消息协议，定义编组请求，解组请求，解组响应规范
 *
 * @author shichao
 * @since 1.0.0
 */
public interface MessageProtocol {
    /**
     * 编组消息请求对象
     *
     * @param request 消息请求的对象
     * @return 编组后的字节数组
     */
    byte[] marshallingRequest(LeisureRequest request) throws Exception;

    /**
     * 解组请求
     *
     * @param data 请求字节数组
     * @return 解组后的消息请求对象
     */
    LeisureRequest unmarshallingRequest(byte[] data) throws Exception;

    /**
     * 编组响应体
     *
     * @param response 响应对象
     * @return 编组后的响应体的字节数组
     */
    byte[] marshallingResponse(LeisureResponse response) throws Exception;

    /**
     * 解组响应体对象
     *
     * @param data 响应的字节数组
     * @return 解组后的响应对象
     */
    LeisureResponse unmarshallingResponse(byte[] data) throws Exception;
}
