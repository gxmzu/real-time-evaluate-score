package com.gxmzu.score.service.impl;

import cn.hutool.json.JSONUtil;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Match;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.mapper.MatchMapper;
import com.gxmzu.score.service.MatchService;
import com.gxmzu.score.service.TokenService;
import com.gxmzu.score.utils.HttpStatus;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:39
 * @Description: 比赛服务实现类
 */
@Service
public class MatchServiceImpl implements MatchService {

    @Resource
    private MatchMapper matchMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    TokenService tokenService;

    //线程池
    private final static ExecutorService CREATE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    @Override
    public List<Match> getMatchList(Match match) {
        return matchMapper.selectMatchList(match);
    }

    @Override
    public AjaxResult addMatch(HttpServletRequest httpServletRequest, Match match) {

        //验证权限
        User user = tokenService.getUser(httpServletRequest);
        if (user.getUserType().equals("0") ||  user.getUserType().equals("2")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }

        //先插入数据，并返回自增主键
        matchMapper.addMatch(match);
        //执行mapper层后，match中封装好了自增的主键、比赛名称、介绍、最高分、打分规则名称、创建人,将封装好的比赛信息转为JSON缓存至redis
        String StringMatch = JSONUtil.toJsonStr(match);
        stringRedisTemplate.opsForValue().set("match:" + match.getMatchId() + ":info",StringMatch);
        return AjaxResult.success("添加成功");
    }

    @Override
    public AjaxResult updateMatch(HttpServletRequest httpServletRequest, Match match) {

        //验证权限
        User user = tokenService.getUser(httpServletRequest);
        if (user.getUserType().equals("0") ||  user.getUserType().equals("2")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }

        //从redis缓存中取出json,并将json转换为bean类型
        String RedisMatch = stringRedisTemplate.opsForValue().get("match:" + match.getMatchId() + ":info");
        if (RedisMatch == null) {
            return AjaxResult.error(HttpStatus.ERROR, "比赛信息不存在");
        }
        Match BeanMatch = JSONUtil.toBean(RedisMatch, Match.class);

        //将bean类型的数据中对应的属性进行修改
        BeanMatch.setMatchName(match.getMatchName());
        BeanMatch.setInfo(match.getInfo());
        BeanMatch.setMaxScore(match.getMaxScore());
        BeanMatch.setScoreRuleName(match.getScoreRuleName());
        BeanMatch.setUpdateBy(match.getUpdateBy());

        //修改完成，将bean类型转换为json并覆盖掉redis中的原数据
        String stringMatch = JSONUtil.toJsonStr(BeanMatch);
        stringRedisTemplate.opsForValue().set("match:" + match.getMatchId() + ":info",stringMatch);

        //在子线程中，对数据库进行数据修改
        CREATE_REBUILD_EXECUTOR.submit(() ->{
            matchMapper.updateMatch(match);
        });

        return AjaxResult.success("更新成功");
    }

    @Override
    public AjaxResult deleteMatch(HttpServletRequest httpServletRequest, Integer matchId) {

        //验证权限
        User user = tokenService.getUser(httpServletRequest);
        if (user.getUserType().equals("0") || user.getUserType().equals("2")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }

        //从Redis中删除参数id对应的数据
        stringRedisTemplate.delete("match:" + matchId + ":info");

        //在子线程中对数据库进行删除操作
        CREATE_REBUILD_EXECUTOR.submit(() ->{
            matchMapper.deleteMatch(matchId);
        });

        return AjaxResult.success("删除成功");

    }
}
