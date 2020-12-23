package com.study.shardingjdbc.dao;


import com.study.shardingjdbc.bean.TestBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDao extends JpaRepository<TestBean,Long> {
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
