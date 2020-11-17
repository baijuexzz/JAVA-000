package day06.singlemode.hungray;

import java.io.*;

/**
 * 饿汉式实现单例模式
 * @desc 饿汉式所表示的是无论是否需要都提前创建
 * 优点：避免了线程安全问题
 * 缺点:1.假设该类一直未被使用，浪费内存资源
 *      2.可被序列化破坏
 *      3.可被反射破坏
 */
public class  HungrySingleMode implements Serializable{

    public static final HungrySingleMode hungrySingleMode=new HungrySingleMode();


    //此处将构造方法修饰为private 让其余类无法通过构造方法去创建函数
    private HungrySingleMode() {
    }

}
