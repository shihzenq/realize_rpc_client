package com.shizhenqiang.rpc.rpc_client;

import com.shizhenqiang.rpc.RpcRequest;
import com.shizhenqiang.rpc.rpc_client.discovery.ServiceDiscovery;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {

    private ServiceDiscovery serviceDiscovery;

    private String version;

    public RemoteInvocationHandler(ServiceDiscovery serviceDiscovery, String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion(version);
        String serviceName = rpcRequest.getClassName();
        if (!StringUtils.isEmpty(version)) {
            serviceName += "-" + version;
        }
        String serviceAddress = serviceDiscovery.discovery(serviceName);
        // 远程调用
        RpcNetTransport rpcNetTransport = new RpcNetTransport(serviceAddress);
        return rpcNetTransport.send(rpcRequest);
    }
}
