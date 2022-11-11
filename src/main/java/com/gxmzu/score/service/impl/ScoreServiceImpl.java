package com.gxmzu.score.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Contestant;
import com.gxmzu.score.domain.entity.Match;
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
     * @param score
     * @return
     */
    @Override
    public AjaxResult scoreTheContestants(HttpServletRequest httpServletRequest, Score score) {

        //验证权限
        User user = tokenService.getUser(httpServletRequest);
        if (user.getUserType().equals("0") || user.getUserType().equals("1")
                || user.getUserType().equals("2")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }

        //获取比赛信息
        String matchJson = stringRedisTemplate.opsForValue().get("match:" + score.getMatchId() + "info");
        Match match = JSONUtil.toBean(matchJson, Match.class);
        if (match == null) {
            return AjaxResult.error(HttpStatus.ERROR, "比赛信息不存在");
        }

        //判断分数是否超出范围
        if (match.getMaxScore() < score.getScore()) {
            return AjaxResult.error(HttpStatus.ERROR_QUERY, "分数超过最大值");
        }

        //从redis中查询json数据并将json数据转为list
        String s = stringRedisTemplate.opsForValue().get("match:" + score.getMatchId() + ":score:" + score.getContestantId());
        JSONArray objects = JSONUtil.parseArray(s);
        List<Score> scores = objects.toList(Score.class);

        score.setProgress(scores.size() + "/" + match.getUserList().length);

        //score.setProgress((scores.size() + 1) + "/" +6);
        score.setCreateBy(user.getUserName());

        //在list中追加新的评分记录
        for (Score obj : scores) {
            if (obj.getMatchId().equals(score.getMatchId()) && obj.getContestantId().equals(score.getContestantId())
                    && obj.getUserId().equals(score.getUserId())) {
                return AjaxResult.error(HttpStatus.ERROR_QUERY, "重复打分");
            }
        }

        //添加到列表
        scores.add(score);

        //将list转为json重新存入redis覆盖掉原有记录
        String jsonStr = JSONUtil.toJsonStr(scores);
        stringRedisTemplate.opsForValue().set("match:" + score.getMatchId() + ":score:" + score.getContestantId(), jsonStr);

        //利用线程池将评分写入数据库
        CREATE_REBUILD_EXECUTOR.submit(()->{
            try{
                scoreMapper.saveScore(score);
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
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
    public AjaxResult openChannel(HttpServletRequest httpServletRequest, Long matchId, Long contestantId) {

        //验证权限
        User user = tokenService.getUser(httpServletRequest);
        if (!user.getUserType().equals("3")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }

        // 取出redis中的参赛者信息
        String json = stringRedisTemplate.opsForValue().get("match:" + matchId + ":isScoring");
        Contestant contestant = JSONUtil.toBean(json, Contestant.class);

        if (contestant == null) {
            return AjaxResult.error(HttpStatus.ERROR, "参赛者信息不存在");
        }

        // 将参赛者信息中的isOpen字段设置为1
        contestant.setIsOpen("1");
        String jsonStr = JSONUtil.toJsonStr(contestant);
        stringRedisTemplate.opsForValue().set("match:" + matchId + ":isScoring", jsonStr);

        //通过新线程将修改写道数据库
        CREATE_REBUILD_EXECUTOR.submit(() ->{
            scoreMapper.openChannel(matchId, contestantId);
        });

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
    public AjaxResult closeChannel(HttpServletRequest httpServletRequest, Long matchId, Long contestantId) {

        //验证权限
        User user = tokenService.getUser(httpServletRequest);
        if (!user.getUserType().equals("3")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }

        //redis中查询到当前正在打分的队伍
        String entityJson = stringRedisTemplate.opsForValue().get("match:" + matchId + ":isScoring");
        Contestant contestant = JSONUtil.toBean(entityJson, Contestant.class);

        if (contestant == null) {
            AjaxResult.error(HttpStatus.ERROR, "队伍不存在");
        }

        //获取打分列表
        String scoreJson = stringRedisTemplate.opsForValue().get("match:" + matchId + ":score:" + contestantId);
        JSONArray objects = JSONUtil.parseArray(scoreJson);
        List<Score> scores = objects.toList(Score.class);

        //获取评分规则并计算分数
        String s = stringRedisTemplate.opsForValue().get("match:" + matchId + "info");
        Match match = JSONUtil.toBean(s, Match.class);
        switch (match.getScoreRuleName()){
            case "去掉最低分，去掉最高分，求平均分":{
                contestant.setScore(CalculateScoreUtils.trimMean(scores));
                break;
            }
            case "平均分": {
                contestant.setScore(CalculateScoreUtils.averageScore(scores));
                break;
            }
            case "总分": {
                contestant.setScore(CalculateScoreUtils.totalScore(scores));
                break;
            }
        }

        //获取下一队参赛队伍的信息
        Long order = contestant.getMatchOrder() + 1;

        //将获取到的队伍存入redis中

        //将修改后的队伍信息写入数据库以及redis中
        CREATE_REBUILD_EXECUTOR.submit(()->{
            //获取redis排行榜
            String rankJson = stringRedisTemplate.opsForValue().get("match:" + matchId + ":rank");
            JSONArray objects1 = JSONUtil.parseArray(rankJson);
            List<Contestant> rank = objects1.toList(Contestant.class);

            //添加记录
            //rank.add();
            String tempJson = JSONUtil.toJsonStr(rank);
            stringRedisTemplate.opsForValue().set("match:" + matchId + ":rank", tempJson);

            //添加到数据库

        });

        return AjaxResult.success("关闭成功");
    }


    /**
     * 从redis中查出评分结束的队伍列表
     *
     * @param matchId 比赛id
     * @return 评分结束的队伍列表
     */
    @Override
    public AjaxResult ScoreList(Long matchId) {
        String rankJson = stringRedisTemplate.opsForValue().get("match:" + matchId + ":rank");
        JSONArray objects1 = JSONUtil.parseArray(rankJson);
        List<Contestant> rank = objects1.toList(Contestant.class);

        return AjaxResult.success(rank);
    }

    /**
     * 获取评分详细信息
     *
     * @param matchId 比赛id
     * @param contestantId 参赛者id
     * @return 评分细节
     */
    @Override
    public AjaxResult detailScoring(Long matchId, Long contestantId) {
        String detailJson = stringRedisTemplate.opsForValue().get("match:" + matchId + ":score:" + contestantId);
        JSONArray objects = JSONUtil.parseArray(detailJson);
        List<Score> scores = JSONUtil.toList(objects, Score.class);

        if (scores.isEmpty()) {
            return AjaxResult.error(HttpStatus.ERROR, "信息不存在");
        }

        return AjaxResult.success(scores);
    }

}
