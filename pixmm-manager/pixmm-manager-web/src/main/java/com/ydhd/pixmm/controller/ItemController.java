package com.ydhd.pixmm.controller;

import com.ydhd.pixmm.pojo.EasyUIDataGridResult;
import com.ydhd.pixmm.pojo.TbItem;
import com.ydhd.pixmm.pojo.TbItemDesc;
import com.ydhd.pixmm.pojo.TbItemParamItem;
import com.ydhd.pixmm.service.ItemService;
import com.ydhd.pixmm.utils.PixmmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 王朋波 on 08/08/2017.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    private TbItem getItemById(@PathVariable Long itemId){
        TbItem item=itemService.getItemById(itemId);
        return item;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    private EasyUIDataGridResult getItemList(Integer page,Integer rows){
        return itemService.getItemList(page,rows);
    }

    @RequestMapping(value="/item/save", method= RequestMethod.POST)
    @ResponseBody
    public PixmmResult createItem(TbItem item, String desc,String itemParams) {
        PixmmResult result = itemService.createItem(item, desc,itemParams);
        return result;
    }

    @RequestMapping(value="/rest/item/update", method= RequestMethod.POST)
    @ResponseBody
    public PixmmResult updateItem(TbItem item, String desc,String itemParams) {
        PixmmResult result = itemService.updateItem(item, desc,itemParams);
        return result;
    }

    @RequestMapping("/showitem/{itemId}")
    public String showItemParam(@PathVariable Long itemId, Model model) {
        String html = itemService.getItemParamHtml(itemId);
        model.addAttribute("html", html);
        return "itemparam";
    }

    @RequestMapping("/rest/item/query/item/desc/{itemId}")
    @ResponseBody
    public PixmmResult getItemDescByItemid(@PathVariable Long itemId){
        TbItemDesc itemDesc = itemService.getItemDescByItemId(itemId);

        return PixmmResult.ok(itemDesc);

    }

    @RequestMapping("/rest/item/query/item/param/{itemId}")
    @ResponseBody
    public PixmmResult getItemParamItemByItemid(@PathVariable Long itemId){
        TbItemParamItem itemParamItem = itemService.getItemParamItemByItemId(itemId);
        return PixmmResult.ok(itemParamItem);
    }


}
