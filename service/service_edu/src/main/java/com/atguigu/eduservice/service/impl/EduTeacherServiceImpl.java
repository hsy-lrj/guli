package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-07
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //查询名师
    @Override
    @Cacheable(value = "eduTeacher", key = "'listeduTeacher'")
    public List<EduTeacher> listeduTeacher(QueryWrapper<EduTeacher> eduTeacherQueryWrapper) {
        List<EduTeacher> eduTeacherList = this.baseMapper.selectList(eduTeacherQueryWrapper);
        return eduTeacherList;
    }
}
