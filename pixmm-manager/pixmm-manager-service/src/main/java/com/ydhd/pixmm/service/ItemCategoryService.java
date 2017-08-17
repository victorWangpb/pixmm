package com.ydhd.pixmm.service;

import com.ydhd.pixmm.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by 王朋波 on 08/08/2017.
 */
public interface ItemCategoryService {
    List<EasyUITreeNode> getItemCategoryList(long parentId);
}
