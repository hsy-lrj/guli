package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-09-08
 */
public interface PayLogService extends IService<PayLog> {
    //生成微信支付二维码
    Map createNative(String orderNo);
    //查询订单状态
    Map<String, String> queryPayStatus(String orderNo);
    //添加记录到支付表，更新订单表的状态信息
    void updateOrderStatus(Map<String, String> map);
}
