package com.example.test.demo.service.Impl;

import com.example.test.demo.service.SomeService;

public class OneSomeService implements SomeService {
    @Override
    public void say() {
        System.out.println("say + :" + this.getClass().getName());;
    }
}
