package com.shizhenqiang.rpc.rpc_client.discovery;

import java.util.List;

public interface LoadBalanceStrategy {

    String selectRepos (List<String> serviceRepos);
}
