package child.leisure.rpc.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具类
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 11:19
 */
public class LeisureRpcUtil {
    private static final Logger logger = LoggerFactory.getLogger(LeisureRpcUtil.class);

    private LeisureRpcUtil() {
    }

    /**
     * 解析地址参数，解析为 主机名和端口号
     *
     * @param address 地址
     * @return [主机名或IP，端口号]
     */
    public static String[] parserServiceAddress(String address) {
        assert address != null : "The parser service address is empty.";
        String[] ipPort = address.split(":");
        assert ipPort.length == 2 : "Fail when parser service address:" + address;
        return ipPort;
    }
}
