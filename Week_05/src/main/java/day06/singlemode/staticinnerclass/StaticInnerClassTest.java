package creationMode.singleMode.staticinnerclass;



import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 静态单例类测试
 */
public class StaticInnerClassTest {

    public static void main(String[] args) {
        try {
            testSingleModeByreflection();
            testSingleModeByDeserialization();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //常规测试单例
    private static void testSingleMode() {
        for (int i = 0; i < 10; i++) {
            StaticInnerClassSingleMode singleInstance = StaticInnerClassSingleMode.getSingleInstance();
            //打印内存地址
            System.out.println(singleInstance);
        }
    }

    //反序列化测试单例
    private static void testSingleModeByDeserialization() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("E:\\testSingle.txt"));
        oos.writeObject(StaticInnerClassSingleMode.getSingleInstance());
        File file = new File("E:\\testSingle.txt");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        StaticInnerClassSingleMode newInstance = (StaticInnerClassSingleMode) ois.readObject();
        if (StaticInnerClassSingleMode.getSingleInstance() == newInstance) {
            System.out.println("静态内部类单例 反序列化后依旧是单例");
        } else {
            System.out.println("静态内部类单例 反序列化破坏单例成功");
        }
    }

    //JAVA反射机制测试单例
    //JAVA的反射机制可以强制访问私有方法或私有变量，所有方法或变量即使设置成为private修饰也会被访问到
    private static void testSingleModeByreflection() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        Class<?> staticInnerSingleMode = Class.forName("creationMode.singleMode.staticinnerclass.StaticInnerClassSingleMode");
        Constructor<?>[] constructors = staticInnerSingleMode.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            //设置私有构造也可访问
            constructor.setAccessible(true);
            StaticInnerClassSingleMode singleMode = (StaticInnerClassSingleMode)constructor.newInstance();
            if (singleMode != StaticInnerClassSingleMode.getSingleInstance()) {
                System.out.println("静态内部类单例 反射破坏单例模式成功");
                return;
            }
        }
        System.out.println("静态内部类单例 反射未成功创建单例");
    }
}
