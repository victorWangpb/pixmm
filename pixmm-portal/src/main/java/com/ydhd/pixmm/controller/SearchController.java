package com.ydhd.pixmm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ydhd.pixmm.search.facade.SearchFacade;
import com.ydhd.pixmm.search.pojo.SearchResult;
import com.ydhd.pixmm.utils.ExceptionUtil;
import com.ydhd.pixmm.utils.PixmmResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 王朋波 on 2017/8/14.
 * solr查询
 */
@Controller
public class SearchController {
    @Reference
    private SearchFacade searchFacade;

    @RequestMapping("/search/q/{keyword}")
    public String search(@PathVariable String keyword,
                         @RequestParam(defaultValue="1")Integer page,
                         @RequestParam(defaultValue="30")Integer rows, Model model) {

        SearchResult searchResult=new SearchResult();
        try {
             searchResult = searchFacade.search(keyword, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //参数传递 给页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getPageCount());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", searchResult.getCurPage());

        return "search";

    }

}
