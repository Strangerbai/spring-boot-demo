package com.example.test.demo.tomcat.servnet;

import java.util.List;
import java.util.Map;

public interface NettyRequest {

    // 获取URI 包含？后边的参数
    String getUri();

    // 获取请求路径
    String getPath();

    // 获取请求方法
    String getMethod();

    // 获取所有的请求参数
    Map<String, List<String>> getParameters();

    // 获取指定名称的请求参数
    List<String> getParameters(String name);

    // 获取指定名称参数的第一个值
    String getParameter(String name);

}
