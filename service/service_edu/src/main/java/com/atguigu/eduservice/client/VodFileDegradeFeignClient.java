package com.atguigu.eduservice.client;

import com.atguigu.commonutils.result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public result removeVideo(String videoId) {
        return result.error().message("删除单个视频出错");
    }

    @Override
    public result removeAllVideo(List<String> videoIdList) {
        return result.error().message("删除多个视频出错");
    }
}
