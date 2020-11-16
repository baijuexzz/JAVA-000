package day4.aop;

public class AopServiceImpl03 implements AOPService {
    @Override
    public void testAOP() {
        System.out.println("未被切面修饰");
    }
}
