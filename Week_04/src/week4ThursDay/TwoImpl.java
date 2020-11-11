package week4ThursDay;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 第二种实现  共享变量实现
 */
public class TwoImpl {

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private static volatile Integer result;

    /**
     * volatile 修饰保证可见
     */
    private static volatile boolean flag=false;

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();

        executorService.execute(()->{
            sum();
        });
        while (true){
            if (!flag){
                Thread.sleep(1000);
            }else {
                break;
            }
        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        executorService.shutdownNow();

    }

    private static int sum() {
        result= fibo(36);
        flag=true;
        return result;
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
