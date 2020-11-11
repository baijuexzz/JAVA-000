package week4ThursDay;

/**
 * 第四种实现 传递值实现
 */
public class FourImpl implements Runnable{

    private Integer resultValue;

    public FourImpl(Integer resultValue) {
        this.resultValue = resultValue;
    }

    public Integer getResultValue() {
        return resultValue;
    }

    public void setResultValue(Integer resultValue) {
        this.resultValue = resultValue;
    }

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        Integer result=null;
        FourImpl four = new FourImpl(result);
        Thread thread = new Thread(four);
        thread.start();
        thread.join();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+four.getResultValue());

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

    }



    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    @Override
    public void run() {
        int sum = sum();
        this.setResultValue(sum);
    }
}
