package com.combile;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: combile-request
 * @description:
 * @author: sch
 * @create: 2021-06-14 20:08
 **/
@SpringBootApplication
@MapperScan(value = {"com.combile.mapper"})
public class CombileRequestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CombileRequestApplication.class);
    }
}
