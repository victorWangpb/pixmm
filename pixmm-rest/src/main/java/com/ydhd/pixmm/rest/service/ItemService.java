package com.ydhd.pixmm.rest.service;

import com.ydhd.pixmm.rest.pojo.ItemInfo;

/**
 * Created by 王朋波 on 2017/8/15.
 */
public interface ItemService {
    ItemInfo getItemById(Long itemId);
    String getItemDescById(Long itemId);
    String getItemParamById(Long itemId);

}
