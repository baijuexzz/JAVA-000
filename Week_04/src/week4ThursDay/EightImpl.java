package week4ThursDay;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock接口实现 此处只演示可重入锁 其他Lock实现与其一直
 */
public class EightImpl {

    private static ReentrantLock lock = new ReentrantLock();

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * 确保子线程先拿到锁
     */
    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    private static volatile Integer result;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        executorService.submit(()->{
            sum();
        });
        countDownLatch.await();
        // 确保  拿到result 并输出
        lock.lock();
        try {
            System.out.println("异步计算结果为：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
        executorService.shutdownNow();
    }

    private static int sum() {
        lock.lock();
        countDownLatch.countDown();
        try {
            result=fibo(36);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
