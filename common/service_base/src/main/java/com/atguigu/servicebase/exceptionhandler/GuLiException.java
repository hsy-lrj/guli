package com.atguigu.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//生成set、get方法
@AllArgsConstructor//生成有参构造方法
@NoArgsConstructor//生成无参构造方法
public class GuLiException extends RuntimeException {

    //状态码
    @ApiModelProperty(value = "状态码")
    private Integer code;
    //错误信息
    private String msg;

}
