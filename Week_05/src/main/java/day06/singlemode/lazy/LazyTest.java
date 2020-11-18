package day06.singlemode.lazy;


import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class LazyTest {

    public static void main(String[] args) {
        try {
            //testSingleModeByManyThread();
            testSingleModeByreflection();
            //testSingleModeByDeserialization();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testSingleModeByManyThread() throws InterruptedException {
        //定义存放实例的线程安全Set
        Set<LazySingleMode> lazySingleModeSets = Collections.synchronizedSet(new HashSet<LazySingleMode>());
        //定义长度为10的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //定义countDownLatch实现 实现线程等待
        CountDownLatch countDownLatch = new CountDownLatch(10);
        //循环10次 子线程异步获取单例实例
        for (int i=0; i<10; i++){
            executorService.submit(()->{
                countDownLatch.countDown();
                try {
                    //确保10线程尽可能同时走到getSingleInstance方法
                    //模拟并发情况单例类的获取
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LazySingleMode singleInstance = LazySingleMode.getSingleInstance();
                lazySingleModeSets.add(singleInstance);
            });
        }
        //等待子线程执行完成
        Thread.sleep(1000);
        System.out.println("多线程情况下 懒汉式生成实例个数为"+lazySingleModeSets.size());
        System.out.println("生成的实例为");
        for (LazySingleMode lazySingleModeSet : lazySingleModeSets) {
            System.out.println(lazySingleModeSet);
        }
    }

    //常规测试单例
    private static void testSingleMode() {
        for (int i = 0; i < 10; i++) {
            LazySingleMode singleInstance = LazySingleMode.getSingleInstance();
            //打印内存地址
            System.out.println(singleInstance);
        }
    }

    //反序列化测试单例
    private static void testSingleModeByDeserialization() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("temp"));
        //将实例写入
        oos.writeObject(LazySingleMode.getSingleInstance());
        File file = new File("temp");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        //将实例读出
        LazySingleMode newInstance = (LazySingleMode) ois.readObject();
        if (LazySingleMode.getSingleInstance() == newInstance) {
            System.out.println("懒汉式单例序列化后依旧是单例");
        } else {
            System.out.println("懒汉式单例 反序列化破坏单例成功");
        }
    }

    //JAVA反射机制测试单例
    //JAVA的反射机制可以强制访问私有方法或私有变量，所有方法或变量即使设置成为private修饰也会被访问到
    private static void testSingleModeByreflection() throws Exception {
        Class<?> lazySingleMode = Class.forName("day06.singlemode.lazy.LazySingleMode");
        Constructor<?>[] constructors = lazySingleMode.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            constructor.setAccessible(true);
            LazySingleMode lazySingleMode1 = (LazySingleMode)constructor.newInstance();
            if (lazySingleMode1 != LazySingleMode.getSingleInstance()) {
                System.out.println("懒汉式单例 反射破坏单例模式成功");
                return;
            }
        }
        System.out.println("懒汉式单例 反射未成功创建单例");
    }
}
