package com.study.hmilytcc.service;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "master")
public interface TestFeignClient {

    @Hmily
    @RequestMapping("/test/update")
    void update();

}
