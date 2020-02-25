package com.shizhenqiang.rpc.rpc_client.discovery;

public interface ServiceDiscovery {

    /**
     * 根据服务名称返回服务地址
     * @param serviceName
     * @return
     */
    String discovery(String serviceName);
}
