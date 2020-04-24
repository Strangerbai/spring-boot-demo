package com.example.test.demo.service.Impl;

import com.example.test.demo.service.RemoteInvoke;
import com.example.test.demo.service.ServiceDiscovery;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.example.rpc.server.common.Invocation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RemoteInvokeImpl implements RemoteInvoke {

    @Resource(name = "serviceDiscoveryImpl")
    ServiceDiscovery serviceDiscovery;


    @Override
    public Object rpcInvoke(String clazz, String method, String[] args, String prefix){
        RpcClientHandler handler = new RpcClientHandler();
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        String serviceAddress = null;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    // Nagle算法，以下属性默认是false，即Nagle算法是开启的
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(handler);
                        }
                    });

            serviceAddress = serviceDiscovery.discovery(clazz);
            // 若zk中不存在该服务，则返回null
            if(serviceAddress == null) {
                return null;
            }
            String host = serviceAddress.split(":")[0];
            String port = serviceAddress.split(":")[1];

            ChannelFuture future = bootstrap.connect(host, Integer.valueOf(port)).sync();

            // 将调用信息实例传递给Server端
            Invocation invocation = new Invocation();
            invocation.setClassName(clazz);
            invocation.setMethodName(method);
            Object[] argsConvert = new Object[args.length];
            for(int i=0;i<args.length;i++){
                argsConvert[i] =  args[i];
            }
            invocation.setParamValues(argsConvert);
            invocation.setPrefix(prefix);

            future.channel().writeAndFlush(invocation).sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }

        return handler.getResult() + " " + serviceAddress;
    }

    @Override
    public Object[] getArgs(String clazz, String method) {
        return new Object[0];
    }
}
