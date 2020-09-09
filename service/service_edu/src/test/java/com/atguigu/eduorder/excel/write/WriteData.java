package com.atguigu.eduorder.excel.write;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

//设置表头和添加的数据字段
@Data
public class WriteData {
    //设置表头名称
    @ExcelProperty("学生编号")
    private int sno;
    
	//设置表头名称
    @ExcelProperty("学生姓名")
    private String sname;
}