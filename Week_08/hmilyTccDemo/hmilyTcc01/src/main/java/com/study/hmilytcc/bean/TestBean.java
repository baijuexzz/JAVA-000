package com.study.hmilytcc.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_order")
public class TestBean {

    @Id    //主键id
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