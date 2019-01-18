package com.jk.controller;

import com.jk.bean.User;
import com.jk.client.LoginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//代表全部方法都有@ResponseBody
public class TestController {
    @Autowired
    LoginClient loginClient;

    @RequestMapping("test")
    public String test(User user){

        return loginClient.invokeLogin2(user);
    }





}
