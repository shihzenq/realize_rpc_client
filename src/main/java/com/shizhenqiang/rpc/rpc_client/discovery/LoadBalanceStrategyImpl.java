package com.shizhenqiang.rpc.rpc_client.discovery;

import java.util.List;
import java.util.Random;

public class LoadBalanceStrategyImpl extends AbstractLoadBalanceStrategy {
    @Override
    protected String doSelectRepos(List<String> serviceRepos) {
        int size = serviceRepos.size();
        Random random = new Random();
        return serviceRepos.get(random.nextInt(size));
    }
}
