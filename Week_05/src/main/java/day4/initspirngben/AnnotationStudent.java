package day4.initspirngben;

import org.springframework.stereotype.Component;

@Component
public class AnnotationStudent {

    private Integer age;

    private String name;

    @Override
    public String toString() {
        return "AnnotationStudent{" +
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

    public AnnotationStudent(Integer age, String name) {
        this.age = age;
        this.name = name;
    }
}
