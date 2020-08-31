package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author atguigu
 * @since 2020-08-31
 */
@RestController
@RequestMapping("/cmsservice/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询
     */
    @GetMapping("/pageBanner/{page}/{limit}")
    public result pageBanner(@PathVariable Long page,@PathVariable Long limit){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        bannerService.page(bannerPage,null);
        return result.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    /**
     * 添加banner
     */
    @PostMapping("/addBanner")
    public result addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return result.ok();
    }

    /**
     *根据id修改banner
     */
    @PutMapping("/updateBannerById")
    public result updateBannerById(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return result.ok();
    }

    /**
     * 根据id删除banner
     * @param id
     * @return
     */
    @DeleteMapping("remove/{id}")
    public result remove(@PathVariable String id) {
        bannerService.removeById(id);
        return result.ok();
    }

    /**
     *根据id获取banner
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    public result get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return result.ok().data("item", banner);
    }


}

