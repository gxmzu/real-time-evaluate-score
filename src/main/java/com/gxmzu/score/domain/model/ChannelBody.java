package com.gxmzu.score.domain.model;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/11
 * @Description: 打分通道开启请求
 */
public class ChannelBody {
    private Long matchId;
    private Long contestantId;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getContestantId() {
        return contestantId;
    }

    public void setContestantId(Long contestantId) {
        this.contestantId = contestantId;
    }
}
