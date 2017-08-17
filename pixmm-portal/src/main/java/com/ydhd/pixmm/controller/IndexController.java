package com.ydhd.pixmm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ydhd.pixmm.rest.facade.ContentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 王朋波 on 12/08/2017.
 */
@Controller
public class IndexController {

    @Reference
    private ContentFacade contentFacade;

    @RequestMapping("/")
    public  String showIndex(Model model){
        String json=contentFacade.getAdList();
        model.addAttribute("ad1",json);
        return "index";
    }
}
