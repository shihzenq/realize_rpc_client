package com.shizhenqiang.rpc.rpc_client;

import com.shizhenqiang.rpc.rpc_client.discovery.LoadBalanceStrategyImpl;
import com.shizhenqiang.rpc.rpc_client.discovery.ServiceDiscovery;
import com.shizhenqiang.rpc.rpc_client.discovery.ServiceDiscoveryImpl;

import java.lang.reflect.Proxy;

public class RpcProxyClient {


    private ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl();

    public <T> T proxyClient(final Class<T> interfaceCls, String version){
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class[]{interfaceCls}, new RemoteInvocationHandler(serviceDiscovery, version));
    }

}
