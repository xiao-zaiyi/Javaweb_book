package com.august.service.impl;

import com.august.pojo.User;
import com.august.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    UserService userService = new UserServiceImpl();


    @Test
    void registerUser() {
        userService.registerUser(new User(null,"admin","123456","110@qq.com"));
    }

    @Test
    void longin() {
        if (userService.longin(new User(null,"admin","1234567",null)) == null){
            System.out.println("登录失败");
        }else{
            System.out.println("登录成功");
        }
    }

    @Test
    void existsUserName() {
       if (userService.existsUserName("admin")){
           System.out.println("用户名已经存在");
       }else{
           System.out.println("用户名可以用");
       }

    }
}