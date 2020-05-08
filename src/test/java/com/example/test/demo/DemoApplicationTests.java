package com.example.test.demo;

import com.example.test.demo.entity.mapper.UserInfoMapper;
import com.example.test.demo.entity.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    UserInfoMapper userInfoMapper;



    @Test
    void contextLoads() {



        // 添加数据
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("test");
        userInfo.setPassword("111111");
        userInfo.setRoles("editor");
        userInfo.setToken("11123232323");
        userInfo.setId(28L);
//        userInfoMapper.insert(userInfo);

        // 更新数据
        userInfo.setPassword("22222");
        userInfoMapper.updateById(userInfo);

        List<UserInfo> userInfos = userInfoMapper.selectList(null);
        userInfos.forEach(info -> System.out.println(info.getUsername()));


    }

}
