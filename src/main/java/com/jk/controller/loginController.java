package com.jk.controller;


import com.alibaba.fastjson.JSONObject;
import com.jk.bean.User;
import com.jk.client.LoginClient;
import com.jk.util.Constant;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
public class loginController {
    @Autowired
    LoginClient loginClient;


    //跳转视图
    @RequestMapping("toLogin")
    public String toLogin(HttpServletRequest request, Model model){

        String msg=null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName()+"====="+cookie.getValue());
            if(Constant.remPwd.equals(cookie.getName())){
                msg=cookie.getValue();
                String[] userAndPwd = msg.split(Constant.splitChar);
                model.addAttribute("username",userAndPwd[0]);
                model.addAttribute("password",userAndPwd[1]);

            }
            }


        }





        return "login";
    }

    //登录
    @ResponseBody
    @RequestMapping("login")
    public String login(User user, HttpSession session, HttpServletResponse response,Model model){

        User userForm=loginClient.invokeLogin(user);

        if(userForm==null){
            //model.addAttribute("msg", "用户名或密码错误");
            return "2";
        }
        //正确
        session.setAttribute("user",user);
        if(user.getRempwd()!=null){

            //json
            //String userText = JSONObject.toJSONString(user);
           //String encode=URLEncoder.encode(userText,enc:"utf-8");
            Cookie cookie= new Cookie(Constant.remPwd,user.getLoginacct()+Constant.splitChar+user.getUserpswd());
            //Cookie pwd = new Cookie("pwd", encode);
            //response.addCookie(pwd);
            cookie.setMaxAge(604800);
            cookie.setPath("/");

           response.addCookie(cookie);

            Cookie test = new Cookie("test","test_val");
            response.addCookie(test);
        }else{
            Cookie cookie = new Cookie(Constant.remPwd,"");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

            return "1";
    }


    //视图
    @RequestMapping("toView")
    public String toView(String viewName){

        return viewName;
    }




}
