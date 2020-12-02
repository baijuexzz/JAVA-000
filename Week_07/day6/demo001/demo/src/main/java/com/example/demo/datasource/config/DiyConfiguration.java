package com.example.demo.datasource.config;

import com.example.demo.datasource.register.DynamicDataSourceRegister;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;




@Configuration
@Import(DynamicDataSourceRegister.class)
public class DiyConfiguration {
}
