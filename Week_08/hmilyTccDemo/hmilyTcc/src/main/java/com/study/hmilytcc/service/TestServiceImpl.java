package com.study.hmilytcc.service;

import com.study.hmilytcc.bean.TestBean;
import com.study.hmilytcc.dao.TestDao;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TestServiceImpl implements TestService{

    @Autowired
    private TestDao testDao;

    @Override
    @HmilyTCC(cancelMethod = "cancle",confirmMethod = "confirm")
    public void update() {
        System.out.println("执行业务逻辑");
        TestBean testBean = new TestBean();
        testBean.setName("UPDATE NAME");
        testBean.setId(10L);
        testDao.save(testBean);
    }

    public void cancle(){
        System.out.println("执行取消方法");
    }

    public void confirm(){
        System.out.println("执行提交方法");
    }
}
