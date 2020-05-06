package com.example.test.demo.tomcat;

import com.example.test.demo.tomcat.servnet.Servnet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TomcatServer {

    private Map<String, Servnet> name2ServnetMap = new ConcurrentHashMap<>();

    private Map<String, String> name2ClassNameMap = new HashMap<>();

    private String basePackage;

    public TomcatServer(String basePackage) {
        this.basePackage = basePackage;
    }

    public void start() throws Exception {
        cacheClassName(basePackage);
        runServer();
    }

    private void runServer() throws Exception {

        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    // 指定存放已经连接上但还来不及处理的请求的队列的长度
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 为了保证请求长连接不被清除掉
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new TomcatHandler(name2ClassNameMap, name2ServnetMap));
                        }
                    });

            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("Tomcat 启动成功");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }

    private void cacheClassName(String basePackage) {
        URL url = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
        if(url == null){
            System.out.println("========");
            return;
        }

        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if(file.isDirectory()){
                cacheClassName(basePackage + "." +  file.getName());
            } else if(file.getName().endsWith(".class")){
                String simpleClassName = file.getName().replace(".class", "").trim();
                name2ClassNameMap.put(simpleClassName.toLowerCase(), basePackage + "." + simpleClassName);
            }
        }
    }

    public static void main(String[] args) {
        try{
            TomcatServer tomcatServer = new TomcatServer("com.example.test.demo.tomcat.webapp");
            tomcatServer.start();
            System.out.println(tomcatServer.name2ClassNameMap);
        } catch (Exception e){
            e.printStackTrace();
        }


    }

}
