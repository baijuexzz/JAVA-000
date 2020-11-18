package day4.divxsd;

import com.sun.org.apache.xml.internal.utils.NameSpace;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 *
 *
 * @org.apache.xbean.Xbean
 */
public class Klazz {

    private String name;

    private String diy;

    public Klazz(String name, String diy) {
        this.name = name;
        this.diy = diy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiy() {
        return diy;
    }

    public void setDiy(String diy) {
        this.diy = diy;
    }
}
