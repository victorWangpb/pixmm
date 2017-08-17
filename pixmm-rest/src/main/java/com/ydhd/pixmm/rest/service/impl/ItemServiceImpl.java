package com.ydhd.pixmm.rest.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydhd.pixmm.dao.TbItemDescMapper;
import com.ydhd.pixmm.dao.TbItemMapper;
import com.ydhd.pixmm.dao.TbItemParamItemMapper;
import com.ydhd.pixmm.pojo.TbItem;
import com.ydhd.pixmm.pojo.TbItemDesc;
import com.ydhd.pixmm.pojo.TbItemParamItem;
import com.ydhd.pixmm.pojo.TbItemParamItemExample;
import com.ydhd.pixmm.rest.pojo.ItemInfo;
import com.ydhd.pixmm.rest.service.ItemService;
import com.ydhd.pixmm.utils.JedisClient;
import com.ydhd.pixmm.utils.JsonUtils;
import com.ydhd.pixmm.utils.PixmmResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;

/**
 * Created by 王朋波 on 2017/8/15.
 */
@org.springframework.stereotype.Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private JedisClient jedisClient;


    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASE_INFO_KEY;
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;
    @Value("${ITEM_DESC_KEY}")
    private String ITEM_DESC_KEY;
    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;
    /**
     * 查询商品基本信息
     * @param itemId
     * @return
     */
    @Override
    public ItemInfo getItemById(Long itemId) {
        //查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId);
            //判断数据是否存在
            if (StringUtils.isNotBlank(json)) {
                // 把json数据转换成java对象
                ItemInfo item = JsonUtils.jsonToPojo(json, ItemInfo.class);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据商品id查询商品基本信息
        ItemInfo itemInfo=new ItemInfo();
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        try {

            String json = JsonUtils.objectToJson(PixmmResult.ok(item));
            PixmmResult result = PixmmResult.formatToPojo(json, ItemInfo.class);

            itemInfo= (ItemInfo) result.getData();
        }catch (Exception e){
            e.printStackTrace();
            return null;

        }


        //向redis中添加缓存。
        //添加缓存原则是不能影响正常的业务逻辑
        try {
            //向redis中添加缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId
                    , JsonUtils.objectToJson(item));
            //设置key的过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId, ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemInfo;
    }


    @Override
    public String getItemDescById(Long itemId) {
        //查询缓存
        //查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY);
            //判断数据是否存在
            if (StringUtils.isNotBlank(json)) {
                // 把json数据转换成java对象
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc.getItemDesc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据商品id查询商品详情
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        //添加缓存
        try {
            //向redis中添加缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY
                    , JsonUtils.objectToJson(itemDesc));
            //设置key的过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY, ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc.getItemDesc();
    }

    @Override
    public String getItemParamById(Long itemId) {
        //添加缓存逻辑
        //查询缓存
        //查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY);
            //判断数据是否存在
            if (StringUtils.isNotBlank(json)) {
                // 把json数据转换成java对象
                TbItemParamItem itemParamitem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return itemParamitem.getParamData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据商品id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        //取规格参数
        if (list != null && list.size() > 0) {
            TbItemParamItem itemParamItem = list.get(0);
            //添加缓存
            try {
                //向redis中添加缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY
                        , JsonUtils.objectToJson(itemParamItem));
                //设置key的过期时间
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY, ITEM_EXPIRE_SECOND);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return itemParamItem.getParamData();
        }
        return null;
    }



}
