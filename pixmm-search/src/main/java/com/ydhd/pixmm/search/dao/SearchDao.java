package com.ydhd.pixmm.search.dao;

import com.ydhd.pixmm.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;


/**
 * Created by 王朋波 on 2017/8/13.
 */
public interface SearchDao {
    SearchResult search(SolrQuery query) throws Exception;
}
