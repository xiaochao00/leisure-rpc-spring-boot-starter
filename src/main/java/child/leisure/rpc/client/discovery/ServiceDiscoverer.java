package child.leisure.rpc.client.discovery;

import child.leisure.rpc.common.service.Service;

import java.util.List;

public interface ServiceDiscoverer {
    /**
     * 得到远端服务列表
     * @return 服务列表
     */
    List<Service> getServices(String name);
}
