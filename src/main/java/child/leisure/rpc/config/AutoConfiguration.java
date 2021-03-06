package child.leisure.rpc.config;

import child.leisure.rpc.client.ClientProxyFactory;
import child.leisure.rpc.client.discovery.ZookeeperServiceDiscoverer;
import child.leisure.rpc.client.net.NettyNetClient;
import child.leisure.rpc.common.protocol.JavaSerializeMessageProtocol;
import child.leisure.rpc.common.protocol.MessageProtocol;
import child.leisure.rpc.properties.LeisureRpcProperty;
import child.leisure.rpc.server.DefaultRpcProcessor;
import child.leisure.rpc.server.net.NettyRpcServer;
import child.leisure.rpc.server.net.RpcServer;
import child.leisure.rpc.server.net.handler.RequestHandler;
import child.leisure.rpc.server.register.ServiceRegister;
import child.leisure.rpc.server.register.ZookeeperExportServiceRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring boot 自动配置类
 *
 * @author 东方雨倾
 * @since 1.0.0
 */
@Configuration
public class AutoConfiguration {

    @Bean
    public DefaultRpcProcessor defaultRpcProcessor() {
        return new DefaultRpcProcessor();
    }

    @Bean
    public ClientProxyFactory clientProxyFactory(@Autowired LeisureRpcProperty leisureRpcProperty) {
        ClientProxyFactory clientProxyFactory = new ClientProxyFactory();
        // 设置服务发现者
        clientProxyFactory.setServiceDiscoverer(new ZookeeperServiceDiscoverer(leisureRpcProperty.getRegisterAddress()));
        // 设置支持的协议
        Map<String, MessageProtocol> supportMessageProtocols = new HashMap<>();
        supportMessageProtocols.put(leisureRpcProperty.getProtocol(), new JavaSerializeMessageProtocol());
        clientProxyFactory.setSupportMessageProtocols(supportMessageProtocols);
        // 设置网络层实现
        clientProxyFactory.setNetClient(new NettyNetClient());
        return clientProxyFactory;
    }

    @Bean
    public ServiceRegister serviceRegister(@Autowired LeisureRpcProperty leisureRpcProperty) {
        return new ZookeeperExportServiceRegister(
                leisureRpcProperty.getRegisterAddress(),
                leisureRpcProperty.getServerPort(),
                leisureRpcProperty.getProtocol());
    }

    @Bean
    public RequestHandler requestHandler(@Autowired ServiceRegister serviceRegister) {
        return new RequestHandler(new JavaSerializeMessageProtocol(), serviceRegister);
    }

    @Bean
    public RpcServer rpcServer(@Autowired RequestHandler requestHandler,
                               @Autowired LeisureRpcProperty leisureRpcProperty) {
        return new NettyRpcServer(leisureRpcProperty.getServerPort(),
                leisureRpcProperty.getProtocol(), requestHandler);
    }

    @Bean
    public LeisureRpcProperty leisureRpcProperty() {
        return new LeisureRpcProperty();
    }
}
