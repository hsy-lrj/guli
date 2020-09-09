package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.commonutils.result;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/eduservice/coursefront")
@RestController
public class CourseFrontContrller {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;



    //多条件分页查询课程
    @PostMapping("/getFrontCoursePageList/{page}/{limit}")
    public result getFrontCoursePageList(@PathVariable long page,@PathVariable long limit,
                                         @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageList = new Page<>(page,limit);
        Map<String, Object> map = eduCourseService.getCourseList(pageList,courseFrontVo);
        return result.ok().data(map);
    }

    //课程详情查询
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public result getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id,编写SQL语句查询课程信息
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id查询课程的支付状态
        //通过Jwt的工具类获取header中存储的数据
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBuy =false;
        if(!StringUtils.isEmpty(memberIdByJwtToken)){
            isBuy = orderClient.isByCourse(courseId, memberIdByJwtToken);
        }
        return result.ok().data("courseWebVo",courseWebVo).data("chapterVoList",chapterVoList).data("isBuy",isBuy).data("memberIdByJwtToken",memberIdByJwtToken);
    }

    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo baseCourseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCourseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
