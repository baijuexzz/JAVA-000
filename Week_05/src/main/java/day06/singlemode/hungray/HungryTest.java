package day06.singlemode.hungray;

import java.io.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class HungryTest {

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
            HungrySingleMode hungrySingleMode = HungrySingleMode.hungrySingleMode;
            //打印内存地址
            System.out.println(hungrySingleMode);
        }
    }

    //反序列化测试单例
    private static void testSingleModeByDeserialization() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("E:\\testSingle.txt"));
        oos.writeObject(HungrySingleMode.hungrySingleMode);
        File file = new File("E:\\testSingle.txt");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        HungrySingleMode newInstance = (HungrySingleMode) ois.readObject();
        if (HungrySingleMode.hungrySingleMode == newInstance) {
            System.out.println("饿汉式单例序列化后依旧是单例");
        } else {
            System.out.println("饿汉式单例 反序列化破坏单例模式成功");
        }
    }

    //JAVA反射机制测试单例
    //JAVA的反射机制可以强制访问私有方法或私有变量，所有方法或变量即使设置成为private修饰也会被访问到
    private static void testSingleModeByreflection() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        Class<?> hungrySingleModeClass = Class.forName("day06.singlemode.hungray.HungrySingleMode");
        Constructor<?>[] constructors = hungrySingleModeClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            constructor.setAccessible(true);
            HungrySingleMode hungrySingleMode1 = (HungrySingleMode)constructor.newInstance();
            if (hungrySingleMode1 != HungrySingleMode.hungrySingleMode) {
                System.out.println("饿汉式单例 反射破坏单例模式成功");
                return;
            }
        }
        System.out.println("饿汉式单例 反射未成功创建单例");
    }
}
