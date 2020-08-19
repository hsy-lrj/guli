package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //先获取所有的章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = this.baseMapper.selectList(chapterQueryWrapper);
        //在获取所有的小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);

        //创建所有章节和小节的集合
        List<ChapterVo> allChapterVideoList = new ArrayList<>();
        //遍历所有章节
        for (EduChapter eduChapter:eduChapters
             ) {
            //将所有章节加入总的集合
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            allChapterVideoList.add(chapterVo);
            //将小节加入到对应的章节中
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVido:eduVideoList
                 ) {
                if(eduVido.getCourseId().equals(eduChapter.getCourseId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVido,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setVideoVoList(videoVoList);
            //allChapterVideoList.add(chapterVo);
        }
        System.out.println(allChapterVideoList);
        return allChapterVideoList;
    }
}
