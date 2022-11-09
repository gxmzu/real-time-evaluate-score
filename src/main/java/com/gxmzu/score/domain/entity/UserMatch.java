package com.gxmzu.score.domain.entity;

import com.gxmzu.score.domain.BaseEntity;

import java.io.Serializable;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:16
 * @Description: 用户比赛类
 */
public class UserMatch extends BaseEntity implements Serializable {

    /**
     * 比赛id
     */
    private long matchId;

    /**
     * 用户id
     */
    private long userId;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserMatch{" +
                "matchId=" + matchId +
                ", userId=" + userId +
                '}';
    }
}
