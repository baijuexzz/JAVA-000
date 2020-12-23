package com.study.shardingjdbc.service;

import com.study.shardingjdbc.bean.TestBean;
import com.study.shardingjdbc.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TestService {


    @Autowired(required = false)
    private TestDao testDao;


    public List<TestBean> getList() {
        return testDao.findAll();
    }


    public TestBean getInfo(Long id) {
        Optional<TestBean> byId = testDao.findById(id);
        return byId.get();
    }


    public void save() {

        TestBean testBean = new TestBean();
        testBean.setName(String.valueOf(System.currentTimeMillis() / 1000));
        try {
            testDao.save(testBean);
        } catch (Exception e) {
            System.out.println(testBean.getId());
            e.printStackTrace();
        }
    }


    public void XXXXXXXXX(Integer id) {
        TestBean testBean = new TestBean();
        testBean.setName("UPDATE NAME");
        testDao.save(testBean);
    }
}
