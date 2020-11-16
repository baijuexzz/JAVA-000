package day4.aop;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import day4.aop.annotations.*;
import day4.aop.cache.AopDemoCache;
import day4.aop.enums.LevelEnums;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class TestAop {

    private static Reflections reflections = new Reflections();

    /**
     * 定义切割符 用于切割方法和类
     */
    public static final String SPILTSTR=",";

    public static void main(String[] args) {
        initMethod();
        for (String s : AopDemoCache.CATHEMAP.keySet()) {
            System.out.println("要修饰的类为:"+s);
            Map<Integer, List<String>> integerListMap = AopDemoCache.CATHEMAP.get(s);
            for (Integer integer : integerListMap.keySet()) {
                System.out.println(" 级别为   "+integer);
                for (String s1 : integerListMap.get(integer)) {
                    System.out.println("执行的方法为："+s1);
                }
            }
        }
        System.out.println("**********************************");
        AOPService aopService= (AOPService) new MetricsCollectorProxy().createProxy(new AopServiceImpl());
        aopService.testAOP();
        System.out.println("#####################");
        AOPService aopService1= (AOPService) new MetricsCollectorProxy().createProxy(new AopServiceImpl02());
        aopService1.testAOP();
        System.out.println("#####################");
        AOPService aopService3= (AOPService) new MetricsCollectorProxy().createProxy(new AopServiceImpl03());
        aopService3.testAOP();
    }

    /**
     * 初始化相关逻辑
     */
    private static void initMethod() {
        Set<Class<?>> classSet = getClazzSetByAspectDemoAnnotaiion();
        if (CollectionUtils.isEmpty(classSet)){
            return;
        }
        for (Class<?> aClass : classSet) {
            //判断该类是否含有切入点注解
            checkisHavePoincutAnnotation(aClass);
            //获取对应得针对某个类要执行的切面方法名称
            getAOPMethod(aClass);
        }
    }



    public static Set<Class<?>> getClazzSetByAspectDemoAnnotaiion()  {
        //获取带AspectDemo注解的类
        Set<Class<?>> classList = reflections.getTypesAnnotatedWith(AspectDemo.class);
        return classList;
    }


    /**
     * 加载对应类 所要执行的切面方法
     * @param clazz
     */
    private static void getAOPMethod(Class<?> clazz) {
        HashMap<Integer, List<String>> levelMethodMaps = new HashMap<>();
        HashSet<String> clazzNames = Sets.newHashSet();
        for (Method method : clazz.getMethods()) {
            Annotation[] annotations = method.getAnnotations();
            if (null !=annotations && annotations.length>0){
                for (Annotation annotation : annotations) {
                    if (annotation instanceof BeforeDemo){
                        List<String> methodNames=new ArrayList<>();
                        if (levelMethodMaps.containsKey(LevelEnums.BEFORE.getLevel())){
                            methodNames = levelMethodMaps.get(LevelEnums.BEFORE.getLevel());
                        }
                        methodNames.add(clazz.getName()+SPILTSTR+method.getName());
                        levelMethodMaps.put(LevelEnums.BEFORE.getLevel(),methodNames);
                    }else if (annotation instanceof AfterDemo){
                        List<String> methodNames=new ArrayList<>();
                        if (levelMethodMaps.containsKey(LevelEnums.AFTER.getLevel())){
                            methodNames = levelMethodMaps.get(LevelEnums.AFTER.getLevel());
                        }
                        methodNames.add(clazz.getName()+SPILTSTR+method.getName());
                        levelMethodMaps.put(LevelEnums.AFTER.getLevel(),methodNames);
                    }else if (annotation instanceof PoincutDemo){
                        PoincutDemo poincutDemo= (PoincutDemo) annotation;
                        for (String clazzName : poincutDemo.clazzNames()) {
                            clazzNames.add(clazzName);
                        }
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(clazzNames)){
            for (String clazzName : clazzNames) {
                if (!AopDemoCache.CATHEMAP.containsKey(clazzName)){
                    AopDemoCache.CATHEMAP.put(clazzName,levelMethodMaps);
                }else {
                    Map<Integer, List<String>> integerListMap = AopDemoCache.CATHEMAP.get(clazzName);
                    for (Integer level : levelMethodMaps.keySet()) {
                        if (integerListMap.containsKey(level)){
                            ArrayList<String> strings = Lists.newArrayList(levelMethodMaps.get(level));
                            strings.addAll(integerListMap.get(level));
                            integerListMap.put(level,strings);
                        }else {
                            integerListMap.put(level,levelMethodMaps.get(level));
                        }
                    }
                    AopDemoCache.CATHEMAP.put(clazzName,integerListMap);
                }
            }
        }

    }

    /**
     * 校验是否含有切面注解
     * @param clazz
     */
    public static void checkisHavePoincutAnnotation(Class<?> clazz){
        for (Method method : clazz.getMethods()) {
            Annotation[] annotations = method.getAnnotations();
            if (null !=annotations && annotations.length>0){
                for (Annotation annotation : annotations) {
                    if (annotation instanceof PoincutDemo){
                        return;
                    }
                }
            }
        }
        throw new RuntimeException("必须包含PoincutDemo注解");
    }


}
