package com.ydhd.pixmm.search.facade;

import com.ydhd.pixmm.search.pojo.SearchResult;

/**
 * Created by 王朋波 on 2017/8/14.
 */
public interface SearchFacade {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
