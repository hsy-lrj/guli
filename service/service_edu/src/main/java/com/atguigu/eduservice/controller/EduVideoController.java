package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-08-18
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @PostMapping("/addVideo")
    public result addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return result.ok();
    }

    /**
     * 删除小节 TODO  后面删除小节的时候，把视频也一起删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteVideo/{id}")
    public result deleteVideo(@PathVariable String id){
        boolean b = eduVideoService.removeById(id);
        if (b){
            return result.ok();
        }else {
            return result.error();
        }
    }

    /**
     * 修改小节
     * @param eduVideo
     * @return
     */
    @PostMapping("/updateVideoById")
    public result updateVideoById(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return result.ok();
    }

    /**
     * 根据id查询小节
     * @param id
     * @return
     */
    @GetMapping("/getVideoById/{id}")
    public result getVideoById(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return result.ok().data("eduVideo",eduVideo);
    }

}

