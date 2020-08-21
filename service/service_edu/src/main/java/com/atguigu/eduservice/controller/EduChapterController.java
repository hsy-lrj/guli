package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-08-19
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 查询所有的章节和小节
     *
     * @param courseId
     * @return
     */
    @GetMapping("/getChapterVideo/{courseId}")
    public result getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);
        return result.ok().data("chapterVoList", chapterVoList);
    }

    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @PostMapping("/addChapter")
    public result addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return result.ok();
    }

    /**
     * 根据id查询章节
     * @param id
     * @return
     */
    @GetMapping("/getChapterById/{id}")
    public result getChapterById(@PathVariable String id){
        EduChapter eduChapter = eduChapterService.getById(id);
        return result.ok().data("eduChapter",eduChapter);
    }

    /**
     *根据id更新章节
     * @param eduChapter
     * @return
     */
    @PostMapping("/updateChapterById")
    public result updateChapterById(@RequestBody EduChapter eduChapter){
        boolean b = eduChapterService.updateById(eduChapter);
        return result.ok();
    }

    /**
     * 根据id删除章节
     * @param chapterId
     * @return
     */
    @DeleteMapping("/deleteChapterById/{chapterId}")
    public result deleteChapterById(@PathVariable String chapterId){
       boolean  flag = eduChapterService.deleteChapterById(chapterId);
       if (flag){
           return result.ok();
       }else {
           return result.error();
       }
    }


}

