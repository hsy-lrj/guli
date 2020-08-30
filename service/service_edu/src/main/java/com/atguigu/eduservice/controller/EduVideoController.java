package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @Autowired
    private VodClient vodClient;

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
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        System.out.println(videoSourceId);
        if (!StringUtils.isEmpty(videoSourceId)){
            //删除视频
            result result = vodClient.removeVideo(videoSourceId);
            if (result.getCode()==20001){
                throw new GuLiException(20001,"删除视频失败，熔断器。。。");
            }
        }
        //删除小节
        eduVideoService.removeById(id);
        return result.ok();
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

