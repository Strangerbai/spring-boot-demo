package com.example.test.demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@Aspect
@Slf4j
public class AOPConfig {
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object simpleAop(final ProceedingJoinPoint pjp) throws Throwable{
        try{
            Object args = pjp.getArgs();
            log.info("args: {}", JSON.toJSONString(args));
            Object o = pjp.proceed();
            log.info("return : {}", JSON.toJSONString(o));
            return o;
        }catch (Throwable e){
            throw e;
        }
    }
}
