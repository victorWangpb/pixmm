package com.ydhd.pixmm.controller;

import com.ydhd.pixmm.pojo.TbContent;
import com.ydhd.pixmm.service.ContentService;
import com.ydhd.pixmm.utils.PixmmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 王朋波 on 13/08/2017.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public List<TbContent> getContentList(Long categoryId){
       return contentService.getContentList(categoryId);
    }

    @RequestMapping("/save")
    @ResponseBody
    public PixmmResult insertContent(TbContent content) {
        PixmmResult result = contentService.insertContent(content);
        return result;
    }

}
