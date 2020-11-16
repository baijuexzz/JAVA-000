package day4.aop.aspects;

import day4.aop.annotations.AfterDemo;
import day4.aop.annotations.AspectDemo;
import day4.aop.annotations.BeforeDemo;
import day4.aop.annotations.PoincutDemo;

@AspectDemo
public class TestAspect {

    /**
     * 定义切入的类  暂时想不到如何像Spring一样支持多种格式
     */
    @PoincutDemo(clazzNames = {"day4.aop.AopServiceImpl","day4.aop.AopServiceImpl02"})
    public void poincut(){}


    /**
     * 前置通知
     * @return
     */
    @BeforeDemo
    public void before(){
        System.out.println("**********前置通知进入************");
    }

    @AfterDemo
    public void after(){
        System.out.println("**********后置通知进入************");
    }




}
