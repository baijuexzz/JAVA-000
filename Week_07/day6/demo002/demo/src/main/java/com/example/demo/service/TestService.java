package com.example.demo.service;

import com.example.demo.beans.TestBean;
import com.example.demo.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TestService {


    @Autowired(required = false)
    private TestDao testDao;



    public List<TestBean> getList(){
        return testDao.findAll();
    }


    public void XXXXXXXXX(Integer id){
        TestBean testBean = new TestBean();
        testBean.setId(id);
        testBean.setName("UPDATE NAME");
        testDao.save(testBean);
    }
}
