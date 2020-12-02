package com.example.demo.dao;



import com.example.demo.beans.TestBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDao extends JpaRepository<TestBean,Integer> {

}
