package day4.divxsd;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 此功能没实现 后续补上
 */
public class DiyNameSpaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        System.out.println("XXXX  ");
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("diy.xsd");
    }
}
