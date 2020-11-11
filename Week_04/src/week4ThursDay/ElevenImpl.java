package week4ThursDay;

import sun.misc.Request;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 实现
 */
public class ElevenImpl {

    private static volatile Integer result;

    private static ReentrantLock reentrantLock=new ReentrantLock();

    private static Condition condition = reentrantLock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        new Thread(()->{
            reentrantLock.lock();
            try {
                sum();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }).start();
        reentrantLock.lock();
        condition.await();
        reentrantLock.unlock();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        result=fibo(36);
        condition.signalAll();
        return result;
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
