package com.cnlive.oicc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.cnlive.oicc.mapper")
public class OiccApplication {

    public static void main(String[] args) {
        SpringApplication.run(OiccApplication.class, args);
    }

}
