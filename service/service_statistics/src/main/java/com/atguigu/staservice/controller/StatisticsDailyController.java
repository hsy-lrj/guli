package com.atguigu.staservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

