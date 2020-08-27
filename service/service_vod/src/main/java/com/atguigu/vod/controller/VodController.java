package com.atguigu.vod.controller;

import com.atguigu.commonutils.result;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}
