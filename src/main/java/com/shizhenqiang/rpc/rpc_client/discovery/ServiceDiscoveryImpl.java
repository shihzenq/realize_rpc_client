package com.shizhenqiang.rpc.rpc_client.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ServiceDiscoveryImpl implements ServiceDiscovery {

    private CuratorFramework curatorFramework = null;

    private List<String> serviceRepos = new ArrayList<>();

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZKConfig.SERVICE_ADDRESS)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("registry")
                .build();
        curatorFramework.start();
    }


    /**
     * 服务查找，设置监听
     *
     * @param serviceName
     * @return
     */
    @Override
    public String discovery(String serviceName) {
        String path = "/" + serviceName;
        if (CollectionUtils.isEmpty(serviceRepos)) {
            try {
                serviceRepos = curatorFramework.getChildren().forPath(path);
                registryWatch(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoadBalanceStrategy loadBalanceStrategy = new LoadBalanceStrategyImpl();
        return loadBalanceStrategy.selectRepos(serviceRepos);
    }

    private void registryWatch(final String path) throws Exception {
        PathChildrenCache noChildNode = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener nodeCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("客户端收到节点变更的事件");
                serviceRepos = curatorFramework.getChildren().forPath(path);
            }
        };
        noChildNode.getListenable().addListener(nodeCacheListener);
        noChildNode.start();
    }
}
