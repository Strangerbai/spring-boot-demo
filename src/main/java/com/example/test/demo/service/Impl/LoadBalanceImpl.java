package com.example.test.demo.service.Impl;

import com.example.test.demo.service.LoadBalance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LoadBalanceImpl implements LoadBalance {
    @Override
    public String choose(List<String> invokerPaths) {
        int index = new Random().nextInt(invokerPaths.size());
        return invokerPaths.get(index);
    }
}
