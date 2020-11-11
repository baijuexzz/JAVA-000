package week4ThursDay;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 第九种实现 基于信号量实现
 */
public class NineImpl {

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private static volatile Integer result;

    private static Semaphore semaphore = new Semaphore(1);

    private static CountDownLatch countDownLatch= new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        executorService.submit(()->{
            try {
                semaphore.acquire();
                countDownLatch.countDown();
                sum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
        });
        countDownLatch.await();
        semaphore.acquire();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
        executorService.shutdownNow();
    }

    private static int sum() {
        return result=fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
