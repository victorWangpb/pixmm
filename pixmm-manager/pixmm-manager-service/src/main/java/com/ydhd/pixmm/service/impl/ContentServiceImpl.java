package com.ydhd.pixmm.service.impl;

import com.ydhd.pixmm.dao.TbContentMapper;
import com.ydhd.pixmm.pojo.TbContent;
import com.ydhd.pixmm.pojo.TbContentExample;
import com.ydhd.pixmm.service.ContentService;
import com.ydhd.pixmm.utils.JedisClient;
import com.ydhd.pixmm.utils.JsonUtils;
import com.ydhd.pixmm.utils.PixmmResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Override
    public List<TbContent> getContentList(Long categoryId) {

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

    @Override
    public PixmmResult insertContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入数据
        contentMapper.insert(content);

        try {
            jedisClient.set(REDIS_CONTENT_KEY,JsonUtils.objectToJson(content));
        }catch (Exception e){
            e.printStackTrace();
        }

        return PixmmResult.ok();

    }
}
