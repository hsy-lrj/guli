package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 分页查询讲师
     */
    @PostMapping("/getTeacherFrontListPage/{page}/{limit}")
    public result getTeacherFrontListPage(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
       Map<String,Object> map = eduTeacherService.getTeacherFrontList(pageTeacher);
        return result.ok().data(map);
    }

    /**
     * 讲师详情功能
     */
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public result getTeacherFrontInfo(@PathVariable String teacherId){
        //根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        //根据讲师id查询所有课程
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> eduCourses = eduCourseService.list(queryWrapper);
        return result.ok().data("eduTeacher",eduTeacher).data("eduCourses",eduCourses);
    }
}
