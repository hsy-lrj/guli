package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2020-08-18
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程确认信息
    CoursePublishVo selectCoursePublishVoById(String courseId);
    //根据课程id,编写SQL语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
