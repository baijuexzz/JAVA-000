package com.study.hmilytcc.controller;

import com.study.hmilytcc.bean.TestBean;
import com.study.hmilytcc.service.TestFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    public static final String SUCCESSMSG="操作成功";

    public static final String ERRORMSG="操作失败";


    @Autowired
    private TestFeignClient testFeignClient;




    @GetMapping("/update")
    public String update(){
        try {
            testFeignClient.update();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改数据库失败");
            return ERRORMSG;
        }
        return SUCCESSMSG;
    }
}
