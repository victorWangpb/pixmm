package com.ydhd.pixmm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ydhd.pixmm.rest.pojo.ItemInfo;
import com.ydhd.pixmm.rest.facade.ItemFacade;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 王朋波 on 2017/8/15.
 */
@Controller
public class ItemController {

    @Reference
    private ItemFacade itemFacade;

    @RequestMapping("/item/{itemId}")
    public String getItemById(@PathVariable Long itemId, Model model){
        ItemInfo item= itemFacade.getItemById(itemId);

        model.addAttribute("item",item);

        return "item";
    }

    @RequestMapping(value="/item/desc/{itemId}", produces= MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemDesc(@PathVariable Long itemId) {
        return itemFacade.getItemDescById(itemId);
    }

    @RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemParam(@PathVariable Long itemId) {
        return itemFacade.getItemParamById(itemId);
    }


}
