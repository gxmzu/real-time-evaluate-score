package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.Match;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:37
 * @Description: 比赛服务
 */
public interface MatchService {

    /**
     * 获取比赛列表
     * @param match 比赛查询信息
     *
     * @return 比赛列表
     */
    public List<Match> getMatchList(Match match);
}
