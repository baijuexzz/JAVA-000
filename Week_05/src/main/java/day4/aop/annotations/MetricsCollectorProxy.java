package day4.aop.annotations;

import day4.aop.TestAop;
import day4.aop.cache.AopDemoCache;
import day4.aop.enums.LevelEnums;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * 用于创建代理类
 */
public class MetricsCollectorProxy {

    public Object createProxy(Object clazz){
        //判断是否需要创建代理对象
        String name = clazz.getClass().getName();
        //根据MAP存储判断是否需要创建代理对象
        //不需要直接返回
        if (!AopDemoCache.CATHEMAP.containsKey(name)){
            return clazz;
        }
        Map<Integer,List<String>> list = AopDemoCache.CATHEMAP.get(name);
        Class<?>[] interfaces = clazz.getClass().getInterfaces();
        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler(clazz,list);
        return Proxy.newProxyInstance(clazz.getClass().getClassLoader(),interfaces,dynamicProxyHandler);
    }


    private class DynamicProxyHandler implements InvocationHandler{


        private Object object;

        private Map<Integer,List<String>> methodMap;

        public DynamicProxyHandler(Object object,Map<Integer,List<String>> methodMap) {
            this.object = object;
            this.methodMap=methodMap;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //执行前置
            if (methodMap.containsKey(LevelEnums.BEFORE.getLevel())){
                for (String s : methodMap.get(LevelEnums.BEFORE.getLevel())) {
                    invokeMethod(s);
                }
            }
            //执行环绕前置
            Object invoke = method.invoke(object, args);
            //执行后置
            if (methodMap.containsKey(LevelEnums.AFTER.getLevel())){
                for (String s : methodMap.get(LevelEnums.AFTER.getLevel())) {
                    invokeMethod(s);
                }
            }

            //返回结果
            return invoke;
        }
    }


    public static void main(String[] args) {
        MetricsCollectorProxy metricsCollectorProxy = new MetricsCollectorProxy();
        metricsCollectorProxy.createProxy(new MetricsCollectorProxy());
    }


    private void invokeMethod(String clazzAndMethodName){
        String[] split = clazzAndMethodName.split(TestAop.SPILTSTR);

        String clazzName=clazzAndMethodName.split(TestAop.SPILTSTR)[0];
        String methodName=clazzAndMethodName.split(TestAop.SPILTSTR)[1];
        try {
            Class<?> aClass = Class.forName(clazzName);
            Method before = aClass.getMethod(methodName);
            before.invoke(aClass.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
