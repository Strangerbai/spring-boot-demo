package com.example.test.demo.service.Impl;

import com.example.test.demo.common.ProjectConstant;
import com.example.test.demo.service.LoadBalance;
import com.example.test.demo.service.ServiceDiscovery;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceDiscoveryImpl implements ServiceDiscovery {
    @Autowired
    private LoadBalance loadBalance;

    private CuratorFramework client;
    private List<String> servers;

    public ServiceDiscoveryImpl() {
        client = CuratorFrameworkFactory.builder()
                .connectString(ProjectConstant.ZK_CLUSTER)
                .connectionTimeoutMs(10000)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        client.start();
    }

    @Override
    public String discovery(String serviceName) {
        try {
            String servicePath = ProjectConstant.ZK_DUBBO_ROOT_PATH + "/" + serviceName;
            servers = client.getChildren()
                    .usingWatcher((CuratorWatcher) event -> {
                        servers = client.getChildren().forPath(servicePath);
                    }).forPath(servicePath);

            if(servers.size() == 0) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadBalance.choose(servers);
    }
}
