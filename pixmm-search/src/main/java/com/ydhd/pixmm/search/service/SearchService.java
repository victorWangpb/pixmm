package com.ydhd.pixmm.search.service;

import com.ydhd.pixmm.search.pojo.SearchResult;

/**
 * Created by 王朋波 on 2017/8/14.
 */
public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
