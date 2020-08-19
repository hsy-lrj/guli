package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-08-18
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/addCourseInfo")
    public result addCourseInfo(@RequestBody CourseInfoVo courseInfoForm){
       String id = eduCourseService.saveCourseInfo(courseInfoForm);
        return result.ok().data("courseId",id);
    }

}

