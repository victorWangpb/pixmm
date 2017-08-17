package com.ydhd.pixmm.service;

import com.ydhd.pixmm.pojo.TbContent;
import com.ydhd.pixmm.utils.PixmmResult;

import java.util.List;

/**
 * Created by 王朋波 on 13/08/2017.
 */
public interface ContentService {
    List<TbContent> getContentList(Long categoryId);
    PixmmResult insertContent(TbContent content);
}
