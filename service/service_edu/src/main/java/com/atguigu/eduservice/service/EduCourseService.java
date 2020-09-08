package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-18
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程信息
     * @param courseInfoForm
     */
    String saveCourseInfo(CourseInfoVo courseInfoForm);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);
    //根据课程id查询课程确认信息
    CoursePublishVo selectCoursePublishVoById(String courseId);

    //根据课程id删除课程
    void removeCourse(String courseId);
    //查询热门课程
    List<EduCourse> listEduCourse(QueryWrapper<EduCourse> eduCourseQueryWrapper);
    //多条件分页查询课程
    Map<String, Object> getCourseList(Page<EduCourse> pageList, CourseFrontVo courseFrontVo);
    //根据课程id,编写SQL语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
