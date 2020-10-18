package clazzLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @desc 加载Hello.xclass文件
 * @author baijue
 */
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> aClass = new HelloClassLoader().findClass("Hello");
            //反射获取对象
            Object hello = aClass.getDeclaredConstructor().newInstance();
            Method hello1 = hello.getClass().getMethod("hello");
            hello1.invoke(hello);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //获取文件位置
        String filePath = HelloClassLoader.class.getResource("Hello.xlass").getFile();
        File file = new File(filePath);
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //写入bytes
            fileInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //执行解密算法
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]=(byte)(255-bytes[i]);
        }
        return defineClass(name,bytes,0, bytes.length);
    }
}
