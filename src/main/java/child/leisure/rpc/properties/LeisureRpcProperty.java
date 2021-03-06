package child.leisure.rpc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 参数配置类，支持用户自定义参数配置
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 20:31
 */
@EnableConfigurationProperties(LeisureRpcProperty.class)
@ConfigurationProperties("leisure.rpc")
public class LeisureRpcProperty {
    /**
     * 服务注册中心
     */
    private String registerAddress = "127.0.0.1:2128";

    /**
     * 服务端暴露的端口号
     */
    private Integer serverPort;

    /**
     * 服务协议
     */
    private String protocol = "leisure";

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "LeisureRpcProperties{" +
                "registerAddress='" + registerAddress + '\'' +
                ", serverPort=" + serverPort +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
