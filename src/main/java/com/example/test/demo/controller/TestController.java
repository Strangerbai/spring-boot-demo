package com.example.test.demo.controller;


import com.example.test.demo.common.Result;
import com.example.test.demo.common.ResultGenerator;
import com.example.test.demo.common.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/say/{msg}")
    public Result<String> say(@PathVariable String msg){
        log.info("start");
        if(msg.equals("error")){
            throw new ServiceException("处理异常");
        }
        String result = "hello spring boot " + msg;
        return ResultGenerator.genSuccessResult(result);
    }
}
