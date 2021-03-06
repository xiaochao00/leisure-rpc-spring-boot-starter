package child.leisure.rpc.client.net;

import child.leisure.rpc.common.service.Service;

/**
 * 网络客户端，定义请求规范
 *
 * @author by shichao
 * @since 1.0.0
 * 2021/3/6 11:09
 */
public interface NetClient {
    /**
     * 向指定的服务发送相关的请求信息
     *
     * @param data    请求的数据内容
     * @param service 指定的服务
     * @return 相应信息
     */
    byte[] sendRequest(byte[] data, Service service) throws InterruptedException;
}
