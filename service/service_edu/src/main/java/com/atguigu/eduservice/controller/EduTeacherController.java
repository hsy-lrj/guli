package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.result;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@RequestMapping("/eduservice/edu_teacher")
@CrossOrigin//解决跨域问题
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
        try {
            int i = 1/0;
        } catch (Exception e) {
            throw new GuLiException(20001,"自定义异常");
        }
        return result.ok().data("items", list);
    }

    /**
     * 根据ID逻辑删除讲师
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("{id}")
    public result removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return result.ok();
        } else {
            return result.error();
        }
    }

    /**
     * 讲师分页
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "讲师分页")
    @GetMapping("pagrteacher/{page}/{limit}")
    public result pageListTeacher(
            @ApiParam(name = "page", value = "当前页数", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页的数量", required = true)
            @PathVariable Integer limit
    ) {
        //创建分页的条件
        Page<EduTeacher> p = new Page<>(page, limit);
        //调用方法
        IPage<EduTeacher> eduTeacherIPage = teacherService.page(p, null);
        long total = eduTeacherIPage.getTotal();
        List<EduTeacher> records = eduTeacherIPage.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("records", records);
        return result.ok().data(map);
    }

    /**
     * 多条件查询带分页
     *
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation("多条件查询带分页")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public result pageTeacherCondition(
            @ApiParam(name = "page", value = "当前页数", required = true)
            @PathVariable("page") Integer page,
            @ApiParam(name = "limit", value = "总页数", required = true)
            @PathVariable("limit") Integer limit,
            @RequestBody(required = false) TeacherQuery teacherQuery
    ) {
        //创建分页对象
        Page<EduTeacher> p = new Page<EduTeacher>(page, limit);
        //构建条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<EduTeacher>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断字段并添加
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        //调用方法
        IPage<EduTeacher> teacherIPage = teacherService.page(p, queryWrapper);
        long total = teacherIPage.getTotal();
        List<EduTeacher> records = teacherIPage.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("records", records);
        return result.ok().data(map);
    }

    /**
     * 新增讲师
     *
     * @param teacher
     * @return
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping
    public result save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {

        boolean flag = teacherService.save(teacher);
        if (flag) {
            return result.ok();
        } else {
            return result.error();
        }


    }

    /**
     * 根据ID查询讲师
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("findById/{id}")
    public result getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        EduTeacher teacher = teacherService.getById(id);
        return result.ok().data("item", teacher);

    }

    /**
     * 根据ID修改讲师
     *
     * @param id
     * @param teacher
     * @return
     */
    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("updateById/{id}")
    public result updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {

        teacher.setId(id);
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return result.ok();
        } else {
            return result.error();
        }
    }


}

