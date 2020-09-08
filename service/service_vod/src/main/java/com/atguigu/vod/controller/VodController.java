package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.result;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.AliyunVodSDKUtils;
import com.atguigu.vod.utils.ConstantPropertiesUtil;
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
    public result uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return result.ok().data("videoId", videoId);
    }

    /**
     * 根据id删除视频
     *
     * @param videoId
     * @return
     */
    @DeleteMapping("/removeVideo/{videoId}")
    public result removeVideo(@PathVariable String videoId) {

        vodService.removeVideo(videoId);
        return result.ok().message("视频删除成功");
    }

    /**
     * 删除所有视频
     *
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/removeAllVideo")
    public result removeAllVideo(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeAllVideo(videoIdList);
        return result.ok();
    }

    /**
     * 获取视频的凭证
     */
    @GetMapping("/getPlayAuth/{id}")
    public result getPlayAuth(@PathVariable String id) {
        try {

            //创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return result.ok().data("playAuth", playAuth);

        } catch (Exception e) {
            throw new GuLiException(20001, "获取凭证失败");
        }

    }

}
