package com.example.test.demo.tomcat.service;

import com.example.test.demo.tomcat.servnet.NettyRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class DefaultNettyRequest implements NettyRequest {

    private HttpRequest httpRequest;

    public DefaultNettyRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public String getUri() {
        return httpRequest.uri();
    }

    @Override
    public String getPath() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.path();
    }

    @Override
    public String getMethod() {
        return httpRequest.method().name();
    }

    @Override
    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.parameters();
    }

    @Override
    public List<String> getParameters(String name) {
        return getParameters().get(name);
    }

    @Override
    public String getParameter(String name) {
        List<String> params = getParameters().get(name);
        if(CollectionUtils.isEmpty(params)){
            return null;
        }
        return params.get(0);

    }
}
