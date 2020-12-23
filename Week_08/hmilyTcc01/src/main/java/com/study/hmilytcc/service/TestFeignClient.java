package com.study.hmilytcc.service;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "provider")
public interface TestFeignClient {

    @GetMapping("/update")
    @Hmily
    void update();
}
