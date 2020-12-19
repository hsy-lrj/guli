package com.atguigu.eduvod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    //上传视频
    String uploadVideo(MultipartFile file);
    //根据id删除视频
    void removeVideo(String videoId);
    //删除所有视频
    void removeAllVideo(List videoIdList);
}
