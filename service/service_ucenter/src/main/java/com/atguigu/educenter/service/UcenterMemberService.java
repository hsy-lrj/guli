package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-09-06
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //登录
    String login(UcenterMember ucenterMember);
    //注册
    void register(RegisterVo registerVo);
    //判断数据库中是否存在扫描人信息，根据openid
    UcenterMember getOpenId(String openid);
    //根据日期查询注册人数
    int CountRegisterDay(String day);
}
