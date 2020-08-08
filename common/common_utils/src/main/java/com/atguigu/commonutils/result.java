package com.atguigu.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果
 */
@Data
public class result {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private result(){}

    /**
     * 成功方法
     * @return
     */
    public static result ok(){
        result r = new result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    /**
     * 失败方法
     * @return
     */
    public static result error(){
        result r = new result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    //返回this的作用是链式编程

    public result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public result message(String message){
        this.setMessage(message);
        return this;
    }

    public result code(Integer code){
        this.setCode(code);
        return this;
    }

    public result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}