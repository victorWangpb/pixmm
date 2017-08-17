package com.ydhd.pixmm.service;

import com.ydhd.pixmm.pojo.EasyUIDataGridResult;
import com.ydhd.pixmm.pojo.TbItem;
import com.ydhd.pixmm.pojo.TbItemDesc;
import com.ydhd.pixmm.utils.PixmmResult;

/**
 * Created by 王朋波 on 08/08/2017.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);

    EasyUIDataGridResult getItemList(int page,int rows);

    PixmmResult createItem(TbItem item,String desc,String itemParam);

    public String getItemParamHtml(Long itemId);

    TbItemDesc getItemDescByItemId(Long itemId);
}
