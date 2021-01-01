package com.baijue.study.distributed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 基于 Bitmap 实现 id 去重
 * bitmap基于string类型
 * string类型最大存储 512M=524288KB=536870912B=4294967296 bit
 * 通过bitmap的offset判断ID是否重复
 */
@Component
@Slf4j
public class Deduplication{

    private static final String REDISKEY="dedup:id";

    private static final Long MAXOFFSET=4294967296L;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 校验ID是否存在
     * @param id
     * @return true代表不存在 false代表存在
     */
    public boolean checkId(Long id){
        boolean flag=false;
        if (null == id){
            throw new NullPointerException("ID 不可为空");
        }
        if (id>MAXOFFSET){
            log.error("ID 超过最大值  传入的ID为{}",id);
            return flag;
        }
        if (redisTemplate.opsForValue().getBit(REDISKEY, id)){
            return flag;
        }
        redisTemplate.opsForValue().setBit(REDISKEY,id,true);
        flag=true;
        return flag;
    }










}
