package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-07
 */
public interface EduTeacherService extends IService<EduTeacher> {
    //查询名师
    List<EduTeacher> listeduTeacher(QueryWrapper<EduTeacher> eduTeacherQueryWrapper);
}
