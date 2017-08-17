package com.ydhd.pixmm.service;

import com.ydhd.pixmm.pojo.EasyUIDataGridResult;
import com.ydhd.pixmm.utils.PixmmResult;

/**
 * Created by 王朋波 on 10/08/2017.
 */
public interface ItemParamService {
    EasyUIDataGridResult getItemParam(Integer page,Integer rows);
    PixmmResult getItemParamByCid(Long cid);
    PixmmResult insertItemParam(Long cid,String paramData);
}
