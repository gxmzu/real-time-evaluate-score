package com.gxmzu.score.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/22
 * @Description: 异步服务
 */
@Service
public class AsyncService {

    private Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Async("threadPoolTaskExecutor")
    public void test(){
        log.info("测试异步线程");
    }
}
