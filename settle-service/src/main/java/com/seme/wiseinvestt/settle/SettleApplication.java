package com.seme.wiseinvestt.settle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.seme.wiseinvestt.api.client")
@MapperScan("com.seme.wiseinvestt.settle.mapper")
@SpringBootApplication
public class SettleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SettleApplication.class, args);
    }
}