package com.atguigu.vod.controller;

import com.atguigu.commonutils.result;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频
     */
    @PostMapping("/uploadVideo")
    public result uploadVideo(MultipartFile file){
       String videoId =  vodService.uploadVideo(file);
        return result.ok().data("videoId",videoId);
    }

    /**
     * 根据id删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/removeVideo/{videoId}")
    public result removeVideo(@PathVariable String videoId){

        vodService.removeVideo(videoId);
        return result.ok().message("视频删除成功");
    }

    /**
     * 删除所有视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/removeAllVideo")
    public result removeAllVideo(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeAllVideo(videoIdList);
        return result.ok();
    }

}
