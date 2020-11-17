package day06.singlemode.doublecheck;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 双重锁加载创建单例
 * 优点：1.线程安全（前提是有volatile修饰的情况下）
 * 2.避免资源消耗（懒加载模型）
 * 缺点：1.可被反射破坏
 * 2.可被序列化破坏
 */

public class DoubleCheckSingleMode {

    //volatile带有的内存屏障保证不会出现在指定重排问题
    private  static volatile DoubleCheckSingleMode doubleCheckSingleMode;

    private DoubleCheckSingleMode() {

    }


    public static DoubleCheckSingleMode getInstance() {
        if (null == doubleCheckSingleMode) {
            //确保同时只有一个线程访问
            synchronized (DoubleCheckSingleMode.class) {
                //避免多个线程进入第二次判断
                if (null == doubleCheckSingleMode) {
                    doubleCheckSingleMode = new DoubleCheckSingleMode();
                }
            }
        }
        return doubleCheckSingleMode;
    }

    public static DoubleCheckSingleMode getInstanceOnlyOneCheck() {
        if (null == doubleCheckSingleMode) {
            //确保同时只有一个线程访问
            System.out.println("线程完成第一次判空校验");
            synchronized (DoubleCheckSingleMode.class) {
                //避免多个线程进入第二次判断
                doubleCheckSingleMode = new DoubleCheckSingleMode();
            }
        }
        return doubleCheckSingleMode;
    }

    public static void main(String[] args) {
        //创建
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Set<DoubleCheckSingleMode> doubleCheckSingleModeSet=new HashSet<>();
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DoubleCheckSingleMode instanceOnlyOneCheck = getInstance();
                doubleCheckSingleModeSet.add(instanceOnlyOneCheck);
            });
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("最终生成实例个数"+doubleCheckSingleModeSet.size());
    }


}
