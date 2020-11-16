package day4.aop.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AOP DEMO的缓存
 */
public class AopDemoCache {

    /**
     * 用于判断是否需要创建代理类的map
     * KEY--->String 为对应得类路径  限制死了 不能动的那种 ....................
     * VALUE--->MAP
     *              KEY---->执行的级别
     *              VALUE---->执行的方法全路径
     *
     */
    public static Map<String, Map<Integer,List<String>>> CATHEMAP=new HashMap<>();
}
