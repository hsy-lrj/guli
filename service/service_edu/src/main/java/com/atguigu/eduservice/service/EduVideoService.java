package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-18
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean getCountByChapterId(String id);
    //根据课程id删除小节
    void deleteVideoByCourseId(String courseId);

}
