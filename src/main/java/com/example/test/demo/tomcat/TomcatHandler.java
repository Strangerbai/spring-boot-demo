package com.example.test.demo.tomcat;

import com.example.test.demo.tomcat.service.DefaultNettyRequest;
import com.example.test.demo.tomcat.service.DefaultNettyResponse;
import com.example.test.demo.tomcat.service.DefaultServnet;
import com.example.test.demo.tomcat.servnet.Servnet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;

public class TomcatHandler extends ChannelInboundHandlerAdapter {

    private Map<String, String> name2ClassNameMap;

    private Map<String, Servnet> name2ServnetMap;

    public TomcatHandler(Map<String, String> name2ClassNameMap, Map<String, Servnet> name2ServnetMap) {
        this.name2ClassNameMap = name2ClassNameMap;
        this.name2ServnetMap = name2ServnetMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String servnetName = request.uri().split("/")[1];
            Servnet servnet = new DefaultServnet();
            if(name2ServnetMap.containsKey(servnetName)){
                servnet = name2ServnetMap.get(servnetName);
            } else if(name2ClassNameMap.containsKey(servnetName)){
                if(name2ServnetMap.get(servnetName) == null){
                    synchronized (this){
                        if(name2ServnetMap.get(servnetName) == null){
                            String className = name2ClassNameMap.get(servnetName);
                            servnet = (Servnet) Class.forName(className).newInstance();
                            name2ServnetMap.put(servnetName, servnet);
                        }
                    }
                }
            }

            DefaultNettyRequest req = new DefaultNettyRequest(request);
            DefaultNettyResponse response = new DefaultNettyResponse(request, ctx);
            if (request.method().name().equalsIgnoreCase("GET")) {
                servnet.doGet(req, response);
            }else if(request.method().name().equalsIgnoreCase("POST")){
                servnet.doPost(req, response);
            }
        }
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
