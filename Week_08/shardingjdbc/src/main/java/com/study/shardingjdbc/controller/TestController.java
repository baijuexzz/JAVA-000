package com.study.shardingjdbc.controller;

import com.study.shardingjdbc.bean.TestBean;
import com.study.shardingjdbc.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLNonTransientException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    public static final String SUCCESSMSG="操作成功";

    public static final String ERRORMSG="操作失败";


    @Autowired
    private TestService testService;

    @GetMapping("/getList")
    public List getList(){
        List<TestBean> list = testService.getList();
        return list;
    }

    @GetMapping("/getInfo")
    public TestBean getInfo(Long id){
        TestBean te = testService.getInfo(id);
        return te;
    }


    @GetMapping("/save")
    public void saveTest(){
        try {
            for (int i = 0; i <1000 ; i++) {
                testService.save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/update")
    public String update(Integer id){
        try {
            testService.XXXXXXXXX(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改数据库失败");
            return ERRORMSG;
        }
        return SUCCESSMSG;
    }
}
