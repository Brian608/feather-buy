package org.feather.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;

import java.util.Arrays;
import java.util.Collections;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-03-14 23:17
 **/
@Component
public class RedisUtils {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long EXCUTE_SUCCESS = 1L;

    /**lua脚本  在redis中 lua脚本执行是串行的 原子的 */
    private static final String UNLOCK_LUA=
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "   return redis.call('del', KEYS[1]) " +
                    "else " +
                    "   return 0 " +
                    "end";

    /**
     *
     * @Description   扣减库存lua脚本
     * @Param
     * @Return 0  key不存在 错误   -1 库存不足  返回list  扣减成功
     */
    public static final String STOCK_REDUCE_LUA=
            "local stock = KEYS[1]\n" +
                    "local stock_lock = KEYS[2]\n" +
                    "local stock_change = tonumber(ARGV[1])\n" +
                    "local stock_lock_change = tonumber(ARGV[2])\n" +
                    "local is_exists = redis.call(\"EXISTS\", stock)\n" +
                    "if is_exists == 1 then\n" +
                    "    local stockAftChange = redis.call(\"INCRBY\", stock,stock_change)\n" +
                    "    if(stockAftChange<0) then\n" +
                    "        redis.call(\"DECRBY\", stock,stock_change)\n" +
                    "        return -1\n" +
                    "    else \n" +
                    "        local stockLockAftChange = redis.call(\"INCRBY\", stock_lock,stock_lock_change)\n" +
                    "        return {stockAftChange,stockLockAftChange}\n" +
                    "    end " +
                    "else \n" +
                    "    return 0\n" +
                    "end";
    public static final String STOCK_CACHE_LUA =
            "local stock = KEYS[1] " +
                    "local stock_lock = KEYS[2] " +
                    "local stock_val = tonumber(ARGV[1]) " +
                    "local stock_lock_val = tonumber(ARGV[2]) " +
                    "local is_exists = redis.call(\"EXISTS\", stock) " +
                    "if is_exists == 1  then " +
                    "   return 0 " +
                    "else  " +
                    "   redis.call(\"SET\", stock, stock_val) " +
                    "   redis.call(\"SET\", stock_lock, stock_lock_val) " +
                    "   return 1 " +
                    "end";

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    /**
     *
     * @Description  分布式锁
     * @Param [lockKey, requestId, expireTime]
     * @Return boolean
     */
    public  boolean distributeLock(String lockKey, String requestId, int expireTime){
        String result = redisTemplate.execute((RedisCallback<String>) redisConnection -> {
            JedisCommands commands = (JedisCommands)redisConnection.getNativeConnection();
            return commands.set(lockKey,requestId,SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME,expireTime);
        });
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @Description 释放分布式锁
     * @Param [lockKey, requestId]
     * @Return boolean
     */
    public boolean releaseDistributelock(String lockKey, String requestId){
        Object result  = redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
            Jedis jedis = (Jedis)redisConnection.getNativeConnection();
            return jedis.eval(UNLOCK_LUA, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        });
        if (EXCUTE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @Description 缓存sku库存 以及锁定库存
     * @Param [stockKey, stockLockKey, stock, stockLock]
     * @Return boolean
     */
    public boolean skuStockInit(String stockKey,String stockLockKey,String stock,String stockLock){
        Object result  = redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
            Jedis jedis = (Jedis)redisConnection.getNativeConnection();
            return jedis.eval(STOCK_CACHE_LUA, Collections.unmodifiableList(Arrays.asList(stockKey,stockLockKey))
                    ,Collections.unmodifiableList(Arrays.asList(stock, stockLock)));
        });
        if (EXCUTE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**4
     *
     * @Description  扣减库存
     * @Param [stockKey, stockLockKey, stockChange, stockLockChange]
     * @Return java.lang.Object
     */
    public Object reduceStock(String stockKey,String stockLockKey,String stockChange,String stockLockChange){
        Object result  = redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
            Jedis jedis = (Jedis)redisConnection.getNativeConnection();
            return jedis.eval(STOCK_REDUCE_LUA, Collections.unmodifiableList(Arrays.asList(stockKey,stockLockKey))
                    ,Collections.unmodifiableList(Arrays.asList(stockChange, stockLockChange)));
        });

        return result;
    }

}
