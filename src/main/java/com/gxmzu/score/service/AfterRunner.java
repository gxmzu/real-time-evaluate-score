package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.Match;
import com.gxmzu.score.domain.entity.Score;
import com.gxmzu.score.mapper.MatchMapper;
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
import java.util.Date;
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
    private AsyncService asyncService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private MatchMapper matchMapper;

    @Override
    @Async("threadPoolTaskExecutor")
    public void run(ApplicationArguments args) {
        log.info("=======加载初始化数据=======");
        log.info("1、测试异步线程");
        asyncService.test();
        log.info("2、加载正在进行的比赛数据到redis");
        List<Match> matchList = matchMapper.selectMatchList(new Match());
        for (int i = 0; i < matchList.size(); i++) {
            Match match = matchList.get(i);
            Date nowDate = new Date();
            Date startDate = match.getStartTime();
            Date endDate = match.getEndTime();
            if (nowDate.before(endDate) && nowDate.after(startDate)) {
                redisCache.deleteObject("score:match:" + match.getMatchId() + ":info");
                redisCache.setCacheObject("score:match:" + match.getMatchId() + ":info", match);
            }
        }
        log.info("3、加载所有评分数据到redis");
        List<Score> scoreList = scoreMapper.selectScoreList(0L);
        Map<Long, List<Score>> map = scoreList.stream().collect(Collectors.groupingBy(Score::getMatchId));
        for (Long matchId : map.keySet()) {
            redisCache.deleteObject("score:match:" + matchId + ":scoring");
            redisCache.setCacheList("score:match:" + matchId + ":scoring", map.get(matchId));
        }
        log.info("=======加载初始化数据完成=======");
    }
}
