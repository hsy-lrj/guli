package com.atguigu.excel.write;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWriteEasyExcel {

    public static void main(String[] args) {
        //实现Excel的写操作
        // 1、设置写入文件夹地址和Excel文件名称
        String fileName = "E:\\write.xlsx";
        /**
         * 2、调用EasyExcel里面的方法实现写操作
         *          参数文件路径名称
         *          参数实体类.class
         */
        EasyExcel.write(fileName, WriteData.class).sheet("写入方法一").doWrite(data());
    }
    //循环设置要添加的数据，最终封装到list集合中
    private static List<WriteData> data() {
        List<WriteData> list = new ArrayList<WriteData>();
        for (int i = 0; i < 10; i++) {
            WriteData data = new WriteData();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }

}
