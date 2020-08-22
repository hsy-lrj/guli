package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
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

    /**
     * 添加课程基本信息
     *
     * @param courseInfoForm
     * @return
     */
    @PostMapping("/addCourseInfo")
    public result addCourseInfo(@RequestBody CourseInfoVo courseInfoForm) {
        String id = eduCourseService.saveCourseInfo(courseInfoForm);
        return result.ok().data("courseId", id);
    }

    /**
     * 根据课程id查询基本信息
     */
    @GetMapping("/getCourseInfo/{courseId}")
    public result getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return result.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 更新课程基本信息
     *
     * @param courseInfoVo
     * @return
     */
    @PostMapping("/updateCourseInfo")
    public result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return result.ok();
    }

    /**
     * 根据课程id查询课程确认信息
     *
     * @param courseId
     * @return
     */
    @GetMapping("/selectCoursePublishVoById/{courseId}")
    public result selectCoursePublishVoById(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = eduCourseService.selectCoursePublishVoById(courseId);
        return result.ok().data("coursePublishVo", coursePublishVo);
    }

    /**
     * 发布课程
     * @param id
     * @return
     */
    @PostMapping("/publishCourse/{id}")
    public result publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        eduCourseService.updateById(eduCourse);
        return result.ok();
    }


}

