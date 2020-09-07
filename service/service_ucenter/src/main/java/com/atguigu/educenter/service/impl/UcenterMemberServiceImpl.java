package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-09-06
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登录
    @Override
    public String login(UcenterMember ucenterMember) {
        //获取登录手机号和密码
        String password = ucenterMember.getPassword();
        String mobile = ucenterMember.getMobile();
        //判断手机号是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuLiException(20001,"登录失败");
        }
        //判断手机号是否为空
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UcenterMember member = this.baseMapper.selectOne(queryWrapper);
        if (member==null){
            throw new GuLiException(20001,"登录失败，手机号不存在");
        }
        //使用MD5进行加密，然后在和数据库中的数据进行对比
        String encrypt = MD5.encrypt(password);
        //判断密码是否正确
        if (!member.getPassword().equals(encrypt)){
            throw new GuLiException(20001,"登录失败，密码不正确");
        }
        //判断禁用状态
        if (member.getIsDisabled()){
            throw new GuLiException(20001,"您已被禁用");
        }
        //登录成功，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取数据
        String mobile = registerVo.getMobile();//手机号
        String password = registerVo.getPassword();//密码
        String nickname = registerVo.getNickname();//昵称
        String code = registerVo.getCode();//验证码

        //判断数据是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)){
            throw new GuLiException(20001,"注册失败，数据不能有空");
        }

        //判断验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new GuLiException(20001,"注册失败,验证码错误");
        }
        //判断手机号是否重复
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if (count>0){
            throw new GuLiException(20001,"注册失败，手机号重复");
        }
        //添加数据
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setIsDeleted(false);
        ucenterMember.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        this.baseMapper.insert(ucenterMember);
    }

    //判断数据库中是否存在扫描人信息，根据openid
    @Override
    public UcenterMember getOpenId(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        return ucenterMember;
    }
}
