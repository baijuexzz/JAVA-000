package com.study.shardingjdbc.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_test")
public class TestBean {

    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.AUTO)//主键生成策略
    @Column(name="id")//数据库字段名
    private Long id;

    @Column(name = "name")
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}