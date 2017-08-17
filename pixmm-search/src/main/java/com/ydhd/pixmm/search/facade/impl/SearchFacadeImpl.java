package com.ydhd.pixmm.search.facade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ydhd.pixmm.search.facade.SearchFacade;
import com.ydhd.pixmm.search.pojo.SearchResult;
import com.ydhd.pixmm.search.service.SearchService;
import com.ydhd.pixmm.utils.PixmmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 王朋波 on 2017/8/14.
 */
@Component("SearchFacade")
@Service
public class SearchFacadeImpl implements SearchFacade {

    @Autowired
    private SearchService searchService;

    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        return searchService.search(queryString, page, rows);
    }
}
