package com.example.demo.test;

public class Student {

    private String age;

    private String name;


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(String age, String name) {
        this.age = age;
        this.name = name;
    }
}
