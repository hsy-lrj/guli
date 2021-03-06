package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2020-09-06
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //根据日期查询注册人数
    int CountRegisterDay(String day);
}
