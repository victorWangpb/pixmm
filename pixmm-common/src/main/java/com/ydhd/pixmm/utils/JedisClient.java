package com.ydhd.pixmm.utils;

/**
 * Created by 王朋波 on 13/08/2017.
 */
public interface JedisClient {
     String set(String key, String value) ;
     String get(String key) ;
     Long hset(String key, String item, String value);
     String hget(String key, String item);
     Long incr(String key);
     Long decr(String key) ;
     Long expire(String key, int second);
     Long ttl(String key);
     Long del(String key);

}
