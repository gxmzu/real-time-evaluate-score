package com.gxmzu.score.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/12
 * @Description: 线程池配置
 */
@EnableAsync
@Configuration
public class ExecutorConfig {

    /**
     * 核心线程池大小
     */
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 最大可创建的线程数
     */
    private int maxPoolSize = 64;

    /**
     * 队列长度
     */
    private int queueSize = 1000;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private int keepAliveSeconds = 300;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor defaultExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("thread-pool-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
