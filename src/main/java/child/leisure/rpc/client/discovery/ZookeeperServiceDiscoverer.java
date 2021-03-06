package child.leisure.rpc.client.discovery;

import child.leisure.rpc.common.serializer.ZookeeperSerializer;
import child.leisure.rpc.common.service.Service;
import child.leisure.rpc.common.constant.LeisureRpcConstant;
import child.leisure.rpc.common.util.LeisureRpcUtil;
import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * zookeeper服务发现
 *
 * @author shichao
 * 2021/3/6 9:38
 */
public class ZookeeperServiceDiscoverer implements ServiceDiscoverer {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperServiceDiscoverer.class);

    private ZkClient zkClient;

    public ZookeeperServiceDiscoverer(String zkAddress) {
        this.zkClient = new ZkClient(zkAddress);
        this.zkClient.setZkSerializer(new ZookeeperSerializer());
    }

    @Override
    public List<Service> getServices(String name) {
        String servicePath = LeisureRpcUtil.combineServicePath(name);
        List<String> children = zkClient.getChildren(servicePath);
        return Optional.ofNullable(children)
                .orElse(new ArrayList<>())
                .stream()
                .map(this::zkChildToService)
                .collect(Collectors.toList());
    }

    private Service zkChildToService(String zkChild) {
        try {
            zkChild = URLDecoder.decode(zkChild, LeisureRpcConstant.UTF8_CODE);
            return JSON.parseObject(zkChild, Service.class);
        } catch (UnsupportedEncodingException e) {
            logger.error("Fail when transform zkNode:{} with error:{}.", zkChild, e.getMessage(), e);
            return null;
        }
    }
}
