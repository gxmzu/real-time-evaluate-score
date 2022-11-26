package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.Score;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10
 * @Description: 打分模块
 */
public interface IScoreService {

    /**
     * 获取某场比赛的评分信息
     *
     * @param matchId 比赛id
     * @return 评分信息
     */
    public List<Score> getScoreList(Long matchId);

}
