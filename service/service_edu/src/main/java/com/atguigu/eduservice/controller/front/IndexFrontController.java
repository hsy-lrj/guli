package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {


    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;


    /**
     * 查询前8个热门课程,前4个名师
     */
    @GetMapping("/index")
    public result index(){
        //查询前8个热门课程
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("id");
        eduCourseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourseList = courseService.listEduCourse(eduCourseQueryWrapper);

        //前4个名师
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByAsc("id");
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = teacherService.listeduTeacher(eduTeacherQueryWrapper);
        //返回数据
        return result.ok().data("eduCourseList",eduCourseList).data("eduTeacherList",eduTeacherList);
    }

}
