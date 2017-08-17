package com.ydhd.pixmm.rest.service.impl;

import com.ydhd.pixmm.dao.TbContentMapper;
import com.ydhd.pixmm.pojo.TbContent;
import com.ydhd.pixmm.pojo.TbContentExample;
import com.ydhd.pixmm.rest.pojo.AdNode;
import com.ydhd.pixmm.rest.service.ContentService;
import com.ydhd.pixmm.utils.JedisClient;
import com.ydhd.pixmm.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋波 on 13/08/2017.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_AD_CATEGORY_ID}")
    private Long INDEX_AD_CATEGORY_ID;
    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Override
    public String getAdList() {
        List<TbContent> contentList=getContentList(INDEX_AD_CATEGORY_ID);
        //把内容列表转换成AdNode列表
        List<AdNode> resultList = new ArrayList<>();
        for (TbContent tbContent : contentList) {
            AdNode node = new AdNode();
            node.setHeight(240);
            node.setWidth(670);
            node.setSrc(tbContent.getPic());

            node.setHeightB(240);
            node.setWidthB(550);
            node.setSrcB(tbContent.getPic2());

            node.setAlt(tbContent.getSubTitle());
            node.setHref(tbContent.getUrl());

            resultList.add(node);
        }
        //需要把resultList转换成json数据
        String resultJson = JsonUtils.objectToJson(resultList);
        return resultJson;

    }

    private List<TbContent> getContentList(Long categoryId) {

        //添加缓存
        //查询数据库之前先查询缓存，如果有直接返回
        try {
            //从redis中取缓存数据
            String json = jedisClient.hget(REDIS_CONTENT_KEY, categoryId+"");
            if (!StringUtils.isBlank(json)) {
                //把json转换成List
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example);

        //返回结果之前，向缓存中添加数据
        try {
            //为了规范key可以使用hash
            //定义一个保存内容的key，hash中每个项就是cid
            //value是list，需要把list转换成json数据。
            jedisClient.hset(REDIS_CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(contentList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentList;
    }
}
