package com.gxmzu.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:28
 * @Description: 启动类
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreApplication.class, args);
    }

}
