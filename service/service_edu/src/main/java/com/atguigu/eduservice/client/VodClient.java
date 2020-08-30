package com.atguigu.eduservice.client;

import com.atguigu.commonutils.result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    /**
     * 根据id删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/eduvod/video/removeVideo/{videoId}")
    public result removeVideo(@PathVariable("videoId") String videoId);

    /**
     * 删除所有视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/eduvod/video/removeAllVideo")
    public result removeAllVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
