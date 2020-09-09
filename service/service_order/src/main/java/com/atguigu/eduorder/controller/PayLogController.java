package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码
     */
    @GetMapping("/createNative/{orderNo}")
    public result createNative(@PathVariable String orderNo){
        //返回一些信息
      Map map = payLogService.createNative(orderNo);
        return result.ok().data(map);
    }

    /**
     * 查询订单状态
     * 参数：订单号
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public result queryPayStatus(@PathVariable String orderNo){
       Map<String,String> map = payLogService.queryPayStatus(orderNo);
       if (map==null){
           return result.error().message("支付出错了");
       }
       //如果返回map不为空，通过map获取订单状态
       if (map.get("trade_state").equals("SUCCESS")){//支付成功
           //添加记录到支付表，更新订单表的状态信息
           payLogService.updateOrderStatus(map);
           return result.ok().message("支付成功");
       }
       return result.ok().code(25000).message("支付中");
    }

}

