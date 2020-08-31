package com.atguigu.cmsservice.service;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-31
 */
public interface CrmBannerService extends IService<CrmBanner> {

    //查询所有的banner
    List<CrmBanner> selectAllBanner();
}
