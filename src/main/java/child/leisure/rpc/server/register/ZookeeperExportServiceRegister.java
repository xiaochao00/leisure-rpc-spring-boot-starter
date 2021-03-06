package child.leisure.rpc.server.register;

import child.leisure.rpc.common.serializer.ZookeeperSerializer;
import child.leisure.rpc.common.service.Service;
import child.leisure.rpc.common.util.LeisureRpcUtil;
import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;

import static child.leisure.rpc.common.constant.LeisureRpcConstant.PATH_DELIMITER;
import static child.leisure.rpc.common.constant.LeisureRpcConstant.UTF8_CODE;

/**
 * zookeeper服务注册器
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 17:21
 */
public class ZookeeperExportServiceRegister extends DefaultServiceRegister implements ServiceRegister {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperExportServiceRegister.class);

    private ZkClient zkClient;

    public ZookeeperExportServiceRegister(String zkAddress, Integer port, String protocol) {
        this.zkClient = new ZkClient(zkAddress);
        this.zkClient.setZkSerializer(new ZookeeperSerializer());
        this.port = port;
        this.protocol = protocol;
    }

    @Override
    public void register(ServiceObject serviceObject) throws Exception {
        super.register(serviceObject);
        Service service = new Service();
        String host = InetAddress.getLocalHost().getHostAddress();
        String address = LeisureRpcUtil.combineServiceAddress(host, this.port);
        service.setName(service.getName());
        service.setAddress(address);
        service.setProtocol(this.protocol);

        this.exportService(service);
    }

    @Override
    public ServiceObject getServerObject(String name) {
        return super.getServerObject(name);
    }

    private void exportService(Service serviceResource) {
        String serverName = serviceResource.getName();
        String uri = JSON.toJSONString(serviceResource);
        try {
            uri = URLEncoder.encode(uri, UTF8_CODE);
        } catch (UnsupportedEncodingException e) {
            logger.error("Fail when encode uri:{}.", uri, e);
        }
        // zk上的永久性节点
        String servicePath = LeisureRpcUtil.combineServicePath(serverName);
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
        }
        // 注册子节点，临时节点
        String uriPath = servicePath + PATH_DELIMITER + uri;
        if(zkClient.exists(uriPath)){
            zkClient.delete(uriPath);
        }
        zkClient.createEphemeral(uriPath);
    }

}
