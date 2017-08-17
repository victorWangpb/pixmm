package com.ydhd.pixmm.controller;

import com.ydhd.pixmm.pojo.PictureResult;
import com.ydhd.pixmm.service.PictureService;
import com.ydhd.pixmm.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 王朋波 on 09/08/2017.
 */
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){

        PictureResult result= pictureService.uploadPic(uploadFile);
        return JsonUtils.objectToJson(result);
    }
}
