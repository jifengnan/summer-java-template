package com.github.jifengnan.template.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TemplateProperties.class)
public class TemplateServiceStarter {
    public static void main(String[] args) {
        SpringApplication.run(TemplateServiceStarter.class, args);
    }
}