package com.ydhd.pixmm.controller;

import com.ydhd.pixmm.pojo.EasyUITreeNode;
import com.ydhd.pixmm.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 王朋波 on 08/08/2017.
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCategoryController {

    @Autowired
    private  ItemCategoryService itemCategoryService;

    @RequestMapping("list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCategoryList(
            @RequestParam(value="id" ,defaultValue = "0")Long parentId){
        return itemCategoryService.getItemCategoryList(parentId);
    }

}
