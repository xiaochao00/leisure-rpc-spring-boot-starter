package child.leisure.rpc.common.service;

/**
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 9:48
 */
public class Service {
    public Service() {
    }

    /**
     * 服务名称
     */
    private String name;
    /**
     * 服务协议
     */
    private String protocol;
    /**
     * 服务地址，格式 IP:port
     */
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", protocol='" + protocol + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
