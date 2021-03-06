package child.leisure.rpc.common.util;

import child.leisure.rpc.common.constant.LeisureRpcConstant;
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

    private static final String ADDRESS_SEPARATOR = ":";

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
        String[] ipPort = address.split(ADDRESS_SEPARATOR);
        assert ipPort.length == 2 : "Fail when parser service address:" + address;
        return ipPort;
    }

    /**
     * 根据主机名和端口号，组装地址
     *
     * @param host 主机名
     * @param port 端口号
     * @return 地址
     */
    public static String combineServiceAddress(String host, Integer port) {
        return String.join(ADDRESS_SEPARATOR, host, port.toString());
    }

    public static String combineServicePath(String serverName) {
        return String.join(LeisureRpcConstant.PATH_DELIMITER, LeisureRpcConstant.ZK_SERVICE_PATH, serverName, "service");
    }
}
