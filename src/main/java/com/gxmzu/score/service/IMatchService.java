package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.Match;
import com.gxmzu.score.domain.entity.UserMatch;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:37
 * @Description: 比赛服务
 */
public interface IMatchService {

    /**
     * 获取比赛列表
     * @param match 比赛查询信息
     *
     * @return 比赛列表
     */
    public List<Match> getMatchList(Match match);

    /**
     * 比赛信息
     * @param userMatch 用户比赛信息
     *
     * @return 比赛信息
     */
    public Match getMatchByUserId(UserMatch userMatch);
}
