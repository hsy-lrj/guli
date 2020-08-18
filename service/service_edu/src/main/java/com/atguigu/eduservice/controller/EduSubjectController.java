package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     * @param file
     * @return
     */
    @PostMapping("/addsubject")
    public result addSubject(MultipartFile file) {
        //获取上传的Excel文件，并把内容读取出来
        subjectService.importSubjectData(file,subjectService);
        return result.ok();
    }

    /**
     *查询课程分类列表（树形结构）
     * @return
     */
    @GetMapping("/list")
    public result getSubjectList(){
       List<OneSubject> allSubjectList = subjectService.getAllSubject();
        return result.ok().data("list",allSubjectList);
    }

}

