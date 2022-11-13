package com.gxmzu.score.service;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Match;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 添加比赛信息
     *
     * @param match 比赛信息
     * @return
     */
    public AjaxResult addMatch(HttpServletRequest httpServletRequest, Match match);

    /**
     * 更新比赛信息
     *
     * @param   match 比赛信息
     * @return
     */
    public AjaxResult updateMatch(HttpServletRequest httpServletRequest, Match match);

    /**
     * 删除比赛信息
     *
     * @param   matchId 比赛id
     * @return
     */
    public AjaxResult deleteMatch(HttpServletRequest httpServletRequest, Integer matchId);
}
