package com.example.test.demo;

import com.example.test.demo.tomcat.TomcatServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.Resource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class DemoApplication implements CommandLineRunner {

    @Resource
    TomcatServer tomcatServer;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        tomcatServer.setBasePackage("com.example.test.demo.tomcat.webapp");
        tomcatServer.start();
    }
}
