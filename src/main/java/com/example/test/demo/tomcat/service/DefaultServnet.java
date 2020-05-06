package com.example.test.demo.tomcat.service;

import com.example.test.demo.tomcat.servnet.NettyRequest;
import com.example.test.demo.tomcat.servnet.NettyResponse;
import com.example.test.demo.tomcat.servnet.Servnet;

public class DefaultServnet extends Servnet {
    @Override
    public void doGet(NettyRequest request, NettyResponse response) {
        String servnetName = request.getUri().split("/")[1];
        response.write("404 -- this servnet : " + servnetName);
    }

    @Override
    public void doPost(NettyRequest request, NettyResponse response) {
        doGet(request, response);
    }
}
