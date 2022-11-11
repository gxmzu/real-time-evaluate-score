package com.gxmzu.score.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Contestant;
import com.gxmzu.score.domain.entity.Score;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.mapper.ScoreMapper;
import com.gxmzu.score.service.ScoreService;
import com.gxmzu.score.service.TokenService;
import com.gxmzu.score.utils.CalculateScoreUtils;
import com.gxmzu.score.utils.HttpStatus;
import com.gxmzu.score.utils.RedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10
 * @Description:
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    ScoreMapper scoreMapper;

    @Resource
    TokenService tokenService;


    //线程池
    private final static ExecutorService CREATE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    /**
     * 以json格式存储评分信息到缓存，每提交一次信息则从缓存取出内容转为list将新提交的内容
     * 转换为json后替换掉redis中原有内容。并开启新的线程将新的分数写入数据库.
     *
     * @param httpServletRequest
     * @param matchId
     * @param userId
     * @param contestantId
     * @param grade
     * @return
     */
    @Override
    public AjaxResult scoreTheContestants(HttpServletRequest httpServletRequest, Long matchId,
                                          Long userId, Long contestantId, Double grade) {

        User user = tokenService.getUser(httpServletRequest);

        Score score1 = new Score();
        score1.setMatchId(matchId);
        score1.setContestantId(contestantId);
        score1.setUserId(userId);
        score1.setScore(grade);
        score1.setCreateBy(user.getUserName());

        //从redis中查询json数据并将json数据转为list
        String s = stringRedisTemplate.opsForValue().get("match:" + matchId + ":score:contestant:" + contestantId);
        JSONArray objects = JSONUtil.parseArray(s);
        List<Score> scores = objects.toList(Score.class);

        //在list中追加新的评分记录
        scores.add(score1);

        //将list转为json重新存入redis覆盖掉原有记录
        String jsonStr = JSONUtil.toJsonStr(scores);
        stringRedisTemplate.opsForValue().set("match:" + matchId + ":score:contestant:" + contestantId, jsonStr);

        //利用线程池将评分写入数据库
        CREATE_REBUILD_EXECUTOR.submit(()->{
            scoreMapper.saveScore(score1);
        });

        return AjaxResult.success("评分成功");
    }


    /**
     * 从redis中查询当前缓存中的队伍，将状态置为1，并改变数据库中的相应内容
     *
     * @param matchId
     * @param contestantId
     * @return
     */
    @Override
    public AjaxResult openChannel(Long matchId, Long contestantId) {
        scoreMapper.openChannel(matchId, contestantId);
        return AjaxResult.success("开启成功");
    }

    /**
     * 1. 从redis中获取正在被打分的队伍，将is_open字段置为0，并按评分规则计算分数写入数据库
     * 2. 查询(当前队伍比赛顺序+1)，将查询到替换掉redis中存放的当前队伍
     * @param matchId
     * @param contestantId
     * @return
     */
    @Override
    public AjaxResult closeChannel(Long matchId, Long contestantId) {
        //redis中查询到当前正在打分的队伍
        String entityJson = stringRedisTemplate.opsForValue().get("match:" + matchId + ":isScoring");
        Contestant contestant = JSONUtil.toBean(entityJson, Contestant.class);

        //获取打分列表
        String scoreJson = stringRedisTemplate.opsForValue().get("match:" + matchId + ":score:contestantId:" + contestantId);
        JSONArray objects = JSONUtil.parseArray(scoreJson);
        List<Score> scores = objects.toList(Score.class);

        //计算分数

        contestant.setScore(CalculateScoreUtils.averageScore(scores));

        //获取下一队参赛队伍的信息
        Long order = contestant.getMatchOrder() + 1;

        //将获取到的队伍存入redis中

        //将修改后的队伍信息写入数据库
        CREATE_REBUILD_EXECUTOR.submit(()->{

        });

        return AjaxResult.success("关闭成功");
    }


}
