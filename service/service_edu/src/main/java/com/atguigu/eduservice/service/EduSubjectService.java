package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-17
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void importSubjectData(MultipartFile file, EduSubjectService subjectService);
    //获取所有的课程分类
    List<OneSubject> getAllSubject();
}
