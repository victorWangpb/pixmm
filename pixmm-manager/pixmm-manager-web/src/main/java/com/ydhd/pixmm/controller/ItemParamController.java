package com.ydhd.pixmm.controller;

import com.ydhd.pixmm.pojo.EasyUIDataGridResult;
import com.ydhd.pixmm.service.ItemParamService;
import com.ydhd.pixmm.utils.PixmmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 王朋波 on 10/08/2017.
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        return itemParamService.getItemParam(page,rows);
    }

    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public PixmmResult getItemCatByCid(@PathVariable Long cid) {
        PixmmResult result = itemParamService.getItemParamByCid(cid);
        return result;
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public PixmmResult insertItemParam(@PathVariable Long cid, String paramData) {
        PixmmResult result = itemParamService.insertItemParam(cid, paramData);
        return result;
    }


}
