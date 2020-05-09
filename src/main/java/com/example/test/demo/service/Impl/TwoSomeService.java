package com.example.test.demo.service.Impl;

import com.example.test.demo.service.SomeService;

public class TwoSomeService implements SomeService {
    @Override
    public void say() {
        System.out.println("say :" + this.getClass().getName());
    }
}
