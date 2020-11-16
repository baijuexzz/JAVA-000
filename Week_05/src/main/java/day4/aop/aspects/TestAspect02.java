package day4.aop.aspects;

import day4.aop.annotations.AfterDemo;
import day4.aop.annotations.AspectDemo;
import day4.aop.annotations.BeforeDemo;
import day4.aop.annotations.PoincutDemo;

@AspectDemo
public class TestAspect02 {

    /**
     * 定义切入的类  暂时想不到如何像Spring一样支持多种格式
     */
    @PoincutDemo(clazzNames = {"day4.aop.AopServiceImpl"})
    public void poincut(){}


    /**
     * 前置通知
     * @return
     */
    @BeforeDemo
    public void before02(){
        System.out.println("**********前置通知02进入************");
    }


    @AfterDemo
    public void after02(){
        System.out.println("**********后置通知02进入************");
    }



}