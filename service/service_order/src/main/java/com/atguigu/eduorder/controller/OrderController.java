package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.result;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     */
    @PostMapping("/createOrder/{courseId}")
    public result createOrder(@PathVariable String courseId, HttpServletRequest request){
      //创建订单，返回订单号
       String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return result.ok().data("orderNo",orderNo);
    }

    /**
     * 根据订单id查询订单
     */
    @GetMapping("/getOrderInfo/{orderId}")
    public result getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderId);
        Order order = orderService.getOne(queryWrapper);
        return result.ok().data("order",order);
    }

    /**
     * 根据课程id和用户id查询课程的支付状态
     */
    @GetMapping("/isByCourse/{courseId}/{memberId}")
    public Boolean isByCourse(@PathVariable String courseId,@PathVariable String memberId){
       QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("course_id",courseId);
       queryWrapper.eq("member_id",memberId);
       queryWrapper.eq("status",1);
        int count = orderService.count(queryWrapper);
        if (count>0){
            return true;
        }else {
            return false;
        }
    }

}

