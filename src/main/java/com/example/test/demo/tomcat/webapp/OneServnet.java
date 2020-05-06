package com.example.test.demo.tomcat.webapp;

import com.example.test.demo.tomcat.servnet.NettyRequest;
import com.example.test.demo.tomcat.servnet.NettyResponse;
import com.example.test.demo.tomcat.servnet.Servnet;

public class OneServnet extends Servnet {
    @Override
    public void doGet(NettyRequest request, NettyResponse response) {
        String uri = request.getUri();
        String method = request.getMethod();
        String name = request.getParameter("name");

        String content = uri + method + name;
        response.write(content);
    }

    @Override
    public void doPost(NettyRequest request, NettyResponse response) {
        doGet(request, response);
    }
}
