package com.atguigu.eduservice.client;

public class OrderFileDegradeFeignClient implements OrderClient {
    @Override
    public Boolean isByCourse(String courseId, String memberId) {
        return Boolean.valueOf("出错了");
    }
}
