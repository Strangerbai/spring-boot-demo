package com.example.test.demo.service;

import org.apache.dubbo.common.extension.SPI;

@SPI("one")
public interface SomeService {
    void say();
}
