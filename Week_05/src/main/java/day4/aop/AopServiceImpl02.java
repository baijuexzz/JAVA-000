package day4.aop;

public class AopServiceImpl02 implements AOPService {
    @Override
    public void testAOP() {
        System.out.println("只被一个切面修饰");
    }
}
