package day4.initspirngben;

import org.springframework.stereotype.Component;

public class XmlStudent {

    private Integer age;

    private String name;

    @Override
    public String toString() {
        return "XmlStudent{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
