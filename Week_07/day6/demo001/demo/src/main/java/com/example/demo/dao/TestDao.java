package com.example.demo.dao;


import com.example.demo.annotation.ReadOnly;
import com.example.demo.annotation.UpdateOnly;
import com.example.demo.beans.TestBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestDao extends JpaRepository<TestBean,Integer> {
/*


    */
/**
     * 查询操作
     * @return
     *//*

    @ReadOnly
    @Override
    List<TestBean> findAll();


    */
/**
     * 修改操作
     * @param testBean
     * @return
     *//*

    @UpdateOnly
    @Override
    TestBean save(TestBean testBean);
*/


}
