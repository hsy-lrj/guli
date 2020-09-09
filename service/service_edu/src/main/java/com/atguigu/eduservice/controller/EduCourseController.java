package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *根据课程id删除课程
     * @param courseId
     * @return
     */
    @DeleteMapping("/deleteCourse/{courseId}")
    public result deleteCourse(@PathVariable String courseId){
        eduCourseService.removeCourse(courseId);
        return result.ok();
    }
    /**
     * 查询所有课程列表
     * @return
     */
    @GetMapping("/getCourseList")
    public result getCourseList(){
        List<EduCourse> list = eduCourseService.list(null);
        return result.ok().data("list",list);
    }
    /**
     * 查询所有课程列表(分页)
     * @return
     */
    @GetMapping("/pageCourseList/{page}/{limit}")
    public result pageCourseList(@PathVariable Integer page,
                                 @PathVariable Integer limit){
        Page<EduCourse> p = new Page<>(page,limit);
        IPage<EduCourse> eduCourseIPage = eduCourseService.page(p, null);
        long total = eduCourseIPage.getTotal();
        List<EduCourse> records = eduCourseIPage.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("records", records);
        return result.ok().data(map);
    }
    /**
     * 查询所有课程列表(多条件分页)
     * @return
     */
    @PostMapping("/pageCourseCondition/{page}/{limit}")
    public result pageCourseCondition(@ApiParam(name = "page", value = "当前页数", required = true)
                                          @PathVariable("page") Integer page,
                                      @ApiParam(name = "limit", value = "总页数", required = true)
                                          @PathVariable("limit") Integer limit,
                                      @RequestBody(required = false) CourseQuery courseQuery
    ){
        //创建分页的条件
        Page<EduCourse> p = new Page<EduCourse>(page, limit);
        //构建条件
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();
        String teacherId = courseQuery.getTeacherId();
        String title = courseQuery.getTitle();
        //判断字段并添加
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title",title);
        }
        //排序
        queryWrapper.orderByDesc("buy_count");
        //调用方法
        IPage<EduCourse> eduCourseIPage =  eduCourseService.page(p,queryWrapper);
        long total = eduCourseIPage.getTotal();
        List<EduCourse> records = eduCourseIPage.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("records", records);
        return result.ok().data(map);
    }

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

