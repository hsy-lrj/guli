package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-08-31
 */
@RequestMapping("/cmsservice/bannerfront")
@CrossOrigin
@RestController
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 查询所有的banner
     */
    @GetMapping("/getAllBanner")
    public result getAllBanner(){
      List<CrmBanner> bannerList =  bannerService.selectAllBanner();
      return result.ok().data("list",bannerList);
    }

}

