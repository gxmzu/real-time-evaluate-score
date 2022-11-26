package com.gxmzu.score.service.impl;

import com.gxmzu.score.domain.entity.Score;
import com.gxmzu.score.mapper.ScoreMapper;
import com.gxmzu.score.service.IScoreService;
import com.gxmzu.score.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10
 * @Description:
 */
@Service
public class IScoreServiceImpl implements IScoreService {

    @Resource
    ScoreMapper scoreMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    public List<Score> getScoreList(Long matchId) {
        return redisCache.getCacheList("score:" + matchId);
//        return scoreMapper.selectScoreList(matchId);
    }


}
