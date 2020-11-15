package day4.initspirngben;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 配置文件实现
 */
public class TestImpl {

    public static void main(String[] args) {
        xmlImpl();
        annotationImpl();
    }


    private static void xmlImpl(){
        System.out.println("XML配置实现");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        XmlStudent bean = applicationContext.getBean(XmlStudent.class);
        System.out.println("XML配置加载完成 "+bean.toString());
    }

    private static void annotationImpl(){
        System.out.println("注解配置实现");
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.registerBean(AnnotationStudent.class,30,"AnnotatiionImpl-BAIJUE");
        annotationConfigApplicationContext.refresh();
        AnnotationStudent bean = annotationConfigApplicationContext.getBean(AnnotationStudent.class);
        System.out.println("注解配置实现  "+bean.toString());
    }
}
