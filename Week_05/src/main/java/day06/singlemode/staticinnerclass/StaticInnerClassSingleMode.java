package creationMode.singleMode.staticinnerclass;

import java.io.Serializable;

/**
 * 静态内部类创建单例
 * @Desc  需要时才创建
 * 优点： 1.线程安全 （由JVM加载内部类保证）
 *        2.避免资源消耗
 * 缺点： 1.可被反射破坏
 *        2.可被序列号破坏
 */
public class StaticInnerClassSingleMode implements Serializable {

    private StaticInnerClassSingleMode(){

    }

    //静态内部类的类加载机制 确保了线程安全
    public static StaticInnerClassSingleMode getSingleInstance(){
        return InnerClass.staticInnerClassSingleMode;
    }

    private static class InnerClass{
        private static StaticInnerClassSingleMode staticInnerClassSingleMode=new StaticInnerClassSingleMode();
    }
}
