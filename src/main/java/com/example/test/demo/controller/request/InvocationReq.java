package com.example.test.demo.controller.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class InvocationReq implements Serializable {

    /**
     * 接口名
     */
    private String className;
    /**
     * 要远程调用的方法名
     */
    private String methodName;
    /**
     * 方法参数类型列表
     */
    private Class<?>[] paramTypes;
    /**
     * 方法参数值
     */
    private String paramValues;
    /**
     * 要调用的业务接口实现类的功能性前辍
     */
    private String prefix;


}
