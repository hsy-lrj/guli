package com.atguigu.excel.read;

import com.alibaba.excel.EasyExcel;

public class TestReadEasyExcel {
    public static void main(String[] args) throws Exception {

        // 写法1：
        String fileName = "E:\\write.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ReadData.class, new ExcelListener()).sheet().doRead();

    }
}
