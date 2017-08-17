package com.ydhd.pixmm.rest.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydhd.pixmm.utils.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by 王朋波 on 2017/8/17.
 */
@Service
public class RabbitmqService {
    @Autowired
    private JedisClient jedisClient;

    private static final ObjectMapper MAPPER=new ObjectMapper();

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASE_INFO_KEY;


    public void manageItemCache(String msg){
        try {
            JsonNode jsonNode=MAPPER.readTree(msg);
            Long itemId=jsonNode.get("itemId").asLong();
            //删除redis缓存
            String key=REDIS_ITEM_KEY+":"+ITEM_BASE_INFO_KEY+":"+itemId;
            jedisClient.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
