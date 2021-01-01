package com.baijue.study.cacheStudy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "study")
@Slf4j
public class CacheStudy  {

    @Autowired
    private CacheStudy cacheStudy;


    /**
     * 接口默认返回结果
     */
    private static String DEFAULT_RESULT="SSSSS";


    private static String UPDATE_RESULT="xxxxxx";

  //  @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("测试 Cache ");
        log.info("第一次获取的值为 {}",cacheStudy.getAll());
        updateResult(UPDATE_RESULT);
        log.info("修改值为 {}",UPDATE_RESULT);
        log.info("第二次获取的值为 {}",cacheStudy.getAll());
    }


    @Cacheable
    public String getAll(){
        return DEFAULT_RESULT;
    }

    @CachePut
    public void updateResult(String updateResult){
        DEFAULT_RESULT=updateResult;
    }
}
