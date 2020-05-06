package com.example.test.demo.tomcat.service;

import com.example.test.demo.tomcat.servnet.NettyResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;

import java.io.UnsupportedEncodingException;

public class DefaultNettyResponse implements NettyResponse {

    private HttpRequest httpRequest;
    private ChannelHandlerContext channelHandlerContext;

    public DefaultNettyResponse(HttpRequest httpRequest, ChannelHandlerContext channelHandlerContext) {
        this.httpRequest = httpRequest;
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void write(String content) {
        try {
            if(StringUtil.isNullOrEmpty(content)){
                return;
            }
            FullHttpResponse defaultHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
            HttpHeaders headers = defaultHttpResponse.headers();
            headers.set(HttpHeaderNames.CONTENT_TYPE, "text/json");
            headers.set(HttpHeaderNames.CONTENT_LENGTH, defaultHttpResponse.content().readableBytes());
            headers.set(HttpHeaderNames.EXPIRES, 0);
            if(HttpUtil.isKeepAlive(httpRequest)){
                headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            channelHandlerContext.writeAndFlush(defaultHttpResponse);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
