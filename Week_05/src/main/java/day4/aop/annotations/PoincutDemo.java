package day4.aop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 切入点注解 demo 此demo只适用于对方法进行切面
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PoincutDemo {

    /**
     * 方法名称全路径  包括类的整体路径
     * @return
     */
    String[] clazzNames();

}
