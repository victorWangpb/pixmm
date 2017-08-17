package com.ydhd.pixmm.controller;

import com.ydhd.pixmm.pojo.EasyUITreeNode;
import com.ydhd.pixmm.service.ContentCategoryService;
import com.ydhd.pixmm.utils.PixmmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 王朋波 on 13/08/2017.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
        List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
        return list;

    }

    @RequestMapping("/create")
    @ResponseBody
    public PixmmResult createNode(Long parentId, String name) {
        PixmmResult result = contentCategoryService.insertCatgory(parentId, name);
        return result;
    }


}
