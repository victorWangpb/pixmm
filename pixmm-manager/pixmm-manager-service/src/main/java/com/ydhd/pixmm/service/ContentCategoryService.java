package com.ydhd.pixmm.service;

import com.ydhd.pixmm.pojo.EasyUITreeNode;
import com.ydhd.pixmm.utils.PixmmResult;

import java.util.List;

/**
 * Created by 王朋波 on 13/08/2017.
 */
public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCatList(Long parentId);
    PixmmResult insertCatgory(Long parentId, String name);
}
