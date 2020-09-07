package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.result;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/edumsm/msm")
@CrossOrigin
@RestController
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 发送短信的方法
     */
    @GetMapping("/sendMsm/{phone}")
    public result sendMsm(@PathVariable String phone){

        //1.从redis中获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return result.ok();
        }
        //2.如果redis获取不到，进行阿里云发送
        //随机生成验证码
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
       boolean flag = msmService.send(param,phone);
       if (flag){
           //发送成功，把成功的验证码放入到redis中，设置redis的有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
           return result.ok();
       }else {
           return result.error().message("短信发送失败");
       }
    }
}
