package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.UserMatch;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/12/04
 * @Description: 用户比赛服务
 */
public interface IUserMatchService {

    /**
     * 获取用户比赛列表
     * @param userMatch 用户比赛信息
     * @return 用户比赛列表
     */
    public List<UserMatch> getUserMatchList(UserMatch userMatch);

    /**
     * 获取用户比赛对象
     * @param userMatch 用户比赛信息
     * @return 用户比赛对象
     */
    public UserMatch getUserMatch(UserMatch userMatch);
}
