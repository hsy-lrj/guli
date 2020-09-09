package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-09-09
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //统计某一天的注册人数
    void registerCount(String day);
}
