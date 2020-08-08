package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-08-07
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 讲师查询所有
     *
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public result list() {
        List<EduTeacher> list = teacherService.list(null);
        return result.ok().data("items", list);
    }

    /**
     * 讲师逻辑删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public result removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if (b) {
            return result.ok();
        } else {
            return result.error();
        }
    }
    @ApiOperation(value = "讲师分页")
    @GetMapping("pagrteacher/{current}/{limit}")
    public result pageListTeacher(
           @ApiParam(name = "current",value = "当前页数",required = true)
           @PathVariable Integer current,
           @ApiParam(name = "limit",value = "每页的数量",required = true)
           @PathVariable Integer limit
    ) {
        //创建分页的条件
        Page<EduTeacher> page = new Page<>(current, limit);
        //调用方法
        IPage<EduTeacher> eduTeacherIPage = teacherService.page(page, null);
        long total = eduTeacherIPage.getTotal();
        List<EduTeacher> records = eduTeacherIPage.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return result.ok().data(map);
    }


}

