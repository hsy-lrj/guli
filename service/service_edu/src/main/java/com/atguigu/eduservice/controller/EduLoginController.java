package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin//解决跨域问题
public class EduLoginController {

    //login
    @RequestMapping("login")
    public result login(){
        return result.ok().data("token","admin");
    }

    //info
    @RequestMapping("info")
    public result info(){
        return result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
