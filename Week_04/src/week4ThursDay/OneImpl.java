package week4ThursDay;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 第一种实现   使用future及callable
 */
public class OneImpl {

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();
        Future<Integer> submit = executorService.submit(() -> {
            return sum();
        });
        System.out.println("异步计算结果为："+submit.get());

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        //关闭线程池
        executorService.shutdownNow();
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
