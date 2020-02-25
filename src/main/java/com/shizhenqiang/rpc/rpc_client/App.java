package com.shizhenqiang.rpc.rpc_client;


import com.shizhenqiang.rpc.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient = applicationContext.getBean(RpcProxyClient.class);
        HelloService helloService = rpcProxyClient.proxyClient(HelloService.class, "v1.0");
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            System.out.println(helloService.meetToSayHello("shizhenqiang"));
        }

    }
}
