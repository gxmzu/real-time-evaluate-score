package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.Score;
import com.gxmzu.score.mapper.ScoreMapper;
import com.gxmzu.score.utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/22
 * @Description: 启动后加载数据类
 */
@Component
@Order(value = 1)
public class AfterRunner implements ApplicationRunner {

    private Logger log = LoggerFactory.getLogger(AfterRunner.class);

    @Resource
    private RedisCache redisCache;

    @Resource
    private ScoreMapper scoreMapper;

    @Override
    @Async("threadPoolTaskExecutor")
    public void run(ApplicationArguments args) throws Exception {
        log.info("=======加载初始化数据=======");
        log.info("1、加载评分数据");
        List<Score> scoreList = scoreMapper.selectScoreList(0L);
        Map<Long, List<Score>> map = scoreList.stream().collect(Collectors.groupingBy(Score::getMatchId));
        for (Long matchId : map.keySet()) {
            redisCache.deleteObject("score:" + matchId);
            redisCache.setCacheList("score:" + matchId, map.get(matchId));
        }
        log.info("=======加载初始化数据完成=======");
    }
}
