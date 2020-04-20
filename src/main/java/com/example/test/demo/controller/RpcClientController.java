package com.example.test.demo.controller;

import com.example.test.demo.common.Result;
import com.example.test.demo.common.ResultGenerator;
import com.example.test.demo.common.ServiceException;
import com.example.test.demo.controller.request.Invocation;
import com.example.test.demo.service.Impl.RemoteInvokeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/rpc")
public class RpcClientController {

    @Resource
    RemoteInvokeImpl remoteInvoke;

    @RequestMapping("/client")
    public Result<Object> remoteInvoke(@RequestBody Invocation invocation){
        // 若调用的是Object的方法，则直接进行本地调用
//        if (Object.class.equals(method.getDeclaringClass())) {
//            return method.invoke(this, args);
//        }
        // 远程调用
        Object result =  remoteInvoke.rpcInvoke(invocation.getClassName(), invocation.getMethodName(), invocation.getParamTypes(), invocation.getPrefix());
        return ResultGenerator.genSuccessResult(result);

    }

}
