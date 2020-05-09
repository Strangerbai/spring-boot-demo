package com.example.test.demo.service;

import org.apache.dubbo.common.extension.ExtensionLoader;

public class SomeServiceTest {
    public static void main(String[] args) {
        ExtensionLoader<SomeService> extensionLoader = ExtensionLoader.getExtensionLoader(SomeService.class);
        SomeService one =  extensionLoader.getExtension("one");
        one.say();
    }
}
