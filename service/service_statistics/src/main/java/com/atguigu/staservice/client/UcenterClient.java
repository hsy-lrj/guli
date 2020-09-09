package com.atguigu.staservice.client;

import com.atguigu.commonutils.result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/educenter/member/CountRegister/{day}")
    public result CountRegister(@PathVariable("day") String day);

}
