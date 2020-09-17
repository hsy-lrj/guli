package com.atguigu.staservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-09-09
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 统计某一天的注册人数
     */
    @PostMapping("/registerCount/{day}")
    public result registerCount(@PathVariable String day){
        statisticsDailyService.registerCount(day);
        return result.ok();
    }

}

