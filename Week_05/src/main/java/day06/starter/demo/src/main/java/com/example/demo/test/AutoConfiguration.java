package com.example.demo.test;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StudentProperties.class)
@ConditionalOnClass(Student.class)
@ConditionalOnProperty(prefix = "test.starter",name = "enbale",havingValue = "true",matchIfMissing = true)
public class AutoConfiguration {

   @Bean
    public Student stu(StudentProperties studentProperties){
        return new Student(studentProperties.getAge(),studentProperties.getName());
    }
}
