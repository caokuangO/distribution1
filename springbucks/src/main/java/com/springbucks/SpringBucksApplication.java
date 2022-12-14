package com.springbucks;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@SpringBootApplication
@Slf4j
@EnableTransactionManagement
public class SpringBucksApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBucksApplication.class, args);
    }

}
