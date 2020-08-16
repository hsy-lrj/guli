package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    /**
     * 上传头像到oss
     *
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        // 通过工具类获取值
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String Filename = file.getOriginalFilename();

            //在文件名称里面添加一个唯一的标识
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            Filename=uuid+Filename;
            //给文件按照日期进行分类
            String time = new DateTime().toString("yyyy/MM/dd");
            Filename=time+"/"+Filename;

            /**
             * 调用oss方法进行上传
             * 参数：
             *      bucket名称
             *      上传到oss文件路径和文件名称
             *      上传文件的输入流
             */
            ossClient.putObject(bucketName, Filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传到阿里云oss的路径手动拼接
            String url = "https://" + bucketName + "." + endpoint + "/" + Filename;

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
