package com.personaltrainer;

import com.personaltrainer.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class PersonalTrainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalTrainerApplication.class, args);
    }

}
