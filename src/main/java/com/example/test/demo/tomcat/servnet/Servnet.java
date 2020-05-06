package com.example.test.demo.tomcat.servnet;

public abstract class Servnet {

    public abstract void doGet(NettyRequest request, NettyResponse response);

    public abstract void doPost(NettyRequest request, NettyResponse response);

}
