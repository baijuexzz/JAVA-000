package week4ThursDay;

/**
 * 第十种实现 基于synhronized的wait notifyAll等方法实现
 */
public class TenImpl {

    private static volatile Integer result;

    /**
     * 将此对象当锁
     */
    private static Object obj=new Object();

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        new Thread(()->{
            sum();
        }).start();
        synchronized (obj){
            obj.wait();
        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        synchronized (obj){
            result=fibo(36);
            obj.notifyAll();
            return result;
        }
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
