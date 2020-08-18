package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    //poi读取excel内容
    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService subjectService) {
        try {
            //1 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20002, "添加课程分类失败");
        }
    }

    /**
     * 获取所有的课程分类
     *
     * @return
     */
    @Override
    public List<OneSubject> getAllSubject() {
        //最终要的到的数据列表
        List<OneSubject> allSubjectList = new ArrayList<>();

        //查询所有一级分类，parent_id==0
        QueryWrapper<EduSubject> queryWrapper1 = new QueryWrapper<EduSubject>();
        queryWrapper1.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(queryWrapper1);

        //查询所有的二级分类，parent_id!=0
        QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(queryWrapper2);

        //进行分类

        for (EduSubject edusuject1 : oneSubjectList
        ) {
            //将一级分类放入到集合中
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(id1);
            //oneSubject.setTitle(edusuject1.getTitle());
            BeanUtils.copyProperties(edusuject1, oneSubject);
            allSubjectList.add(oneSubject);

            //将二级分类放入集合中
            List<TwoSubject> twoSubjectList1 = new ArrayList<>();
            for (EduSubject edusubject2 : twoSubjectList
            ) {
                if (edusuject1.getId().equals(edusubject2.getParentId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    //twoSubject.setId(id2);
                    //twoSubject.setTitle(edusubject2.getTitle());
                    BeanUtils.copyProperties(edusubject2, twoSubject);
                    twoSubjectList1.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjectList1);
        }

        return allSubjectList;
    }

}
