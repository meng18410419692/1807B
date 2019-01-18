package com.jk.client;

import com.jk.bean.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("provider-login")
public interface LoginClient {
    @RequestMapping("login")
    User invokeLogin(User user);


    @RequestMapping("test")
    String invokeLogin2(User user);


}
