package com.atguigu.eduservice.client;

import com.atguigu.commonutils.result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-vod")
public interface VodClient {

    /**
     * 删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/eduvod/video/removeVideo/{videoId}")
    public result removeVideo(@PathVariable("videoId") String videoId);
}
