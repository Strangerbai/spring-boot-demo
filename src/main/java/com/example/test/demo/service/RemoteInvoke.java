package com.example.test.demo.service;

public interface RemoteInvoke {

    Object rpcInvoke(String clazz, String method, String[] args, String prefix);


    Object[] getArgs(String clazz, String method);
}
