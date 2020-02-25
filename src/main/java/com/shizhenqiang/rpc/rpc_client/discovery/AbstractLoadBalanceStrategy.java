package com.shizhenqiang.rpc.rpc_client.discovery;

import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class AbstractLoadBalanceStrategy implements LoadBalanceStrategy{

    @Override
    public String selectRepos(List<String> serviceRepos) {
        if (CollectionUtils.isEmpty(serviceRepos)) return null;
        if (serviceRepos.size() == 1) {
            return serviceRepos.get(0);
        }
        return doSelectRepos(serviceRepos);
    }

    protected abstract String doSelectRepos(List<String> serviceRepos);
}
