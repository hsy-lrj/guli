package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.result;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-09-06
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public result login(@RequestBody UcenterMember ucenterMember) {
        String token = ucenterMemberService.login(ucenterMember);
        return result.ok().data("token", token);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public result registerUser(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return result.ok();
    }

    @GetMapping("/getMemberInfo")
    public result getMemberInfo(HttpServletRequest request){
        try {
            //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            //查询数据库，根据用户id获取用户信息
            UcenterMember member = ucenterMemberService.getById(memberId);
            return result.ok().data("userInfo", member);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuLiException(20001,"error");
        }
    }

}

