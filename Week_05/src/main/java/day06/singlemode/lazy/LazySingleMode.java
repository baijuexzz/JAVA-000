package day06.singlemode.lazy;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 懒汉式单例
 *
 * @desc 只有用到的时候才会创建
 * 优点：只有被使用时才会被实例化，避免了内存资源的消耗
 * 缺点：1.线程安全问题，多线程同时访问代码块(1)
 *       2.可被序列化破坏
 *       3.可被反射破坏
 */
public class LazySingleMode implements Serializable {

    private static LazySingleMode lazySingleMode;

    //private 修饰确保外部不会直接通过构造方法创建对象
    private LazySingleMode() {
        System.out.println("cj");
    }

    public static LazySingleMode getSingleInstance() {
        //判断单例对象是否为空
        if (null == lazySingleMode) {
            lazySingleMode = new LazySingleMode();
        }
        return lazySingleMode;
    }

    //外部获取
    public static void main(String[] args) {
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
                System.out.println(singleInstance);
            });
        }
    }
}
