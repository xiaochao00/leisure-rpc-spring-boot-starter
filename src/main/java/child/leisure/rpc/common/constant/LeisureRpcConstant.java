package child.leisure.rpc.common.constant;

import java.nio.charset.StandardCharsets;

/**
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 10:46
 */
public class LeisureRpcConstant {
    private LeisureRpcConstant() {
    }

    /**
     * zookeeper 服务注册的地址
     */
    public static final String ZK_SERVICE_PATH = "/leisure-rpc";

    /**
     * 编码
     */
    public static final String ENCODE_CODE = StandardCharsets.UTF_8.toString();

    /**
     * 路径分隔符
     */
    public static final String PATH_DELIMITER = "/";


}
