package week4ThursDay;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 第七种实现 基于CyclicBarrier实现
 */
public class SevenImpl {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private static volatile Integer result;

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        executorService.submit(()->{
            sum();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        cyclicBarrier.await();
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
