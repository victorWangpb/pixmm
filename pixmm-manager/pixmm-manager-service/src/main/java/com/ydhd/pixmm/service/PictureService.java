package com.ydhd.pixmm.service;

import com.ydhd.pixmm.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 王朋波 on 09/08/2017.
 */
public interface PictureService {
    PictureResult uploadPic(MultipartFile picFile);
}
