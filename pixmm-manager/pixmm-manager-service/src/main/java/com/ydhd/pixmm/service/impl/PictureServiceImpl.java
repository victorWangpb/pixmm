package com.ydhd.pixmm.service.impl;

import com.ydhd.pixmm.pojo.PictureResult;
import com.ydhd.pixmm.service.PictureService;
import com.ydhd.pixmm.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by 王朋波 on 09/08/2017.
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Value("${IMAGE_SERVER_BASE_URL}")
    private String IMAGE_SERVER_BASE_URL;

    @Override
    public PictureResult uploadPic(MultipartFile picFile) {

        PictureResult result=new PictureResult();
        //判断图片是否为空
        if(picFile.isEmpty()){
            result.setError(1);
            result.setMessage("图片为空");
            return result;
        }

        //上传
        try {
            //获取图片扩展名
            String originalFileName=picFile.getOriginalFilename();
            //取扩展名，不要“.”
            String extName=originalFileName.
                    substring(originalFileName.lastIndexOf(".")+1);
            FastDFSClient client=new FastDFSClient("classpath:prof/client.conf");
            String url=client.uploadFile(picFile.getBytes(),extName);

            //拼接服务器地址
            url=IMAGE_SERVER_BASE_URL+url;

            //把url响应给客户都
            result.setError(0);
            result.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1);
            result.setMessage("上传图片失败");
        }

        return result;
    }
}
