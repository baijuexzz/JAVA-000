package day4.aop.aspects;

import day4.aop.annotations.AfterDemo;
import day4.aop.annotations.AspectDemo;
import day4.aop.annotations.BeforeDemo;
import day4.aop.annotations.PoincutDemo;

@AspectDemo
public class TestAspect03 {

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
    public void before03(){
        System.out.println("**********前置通知03进入************");
    }


    @AfterDemo
    public void after03(){
        System.out.println("**********后置通知03进入************");
    }
}
