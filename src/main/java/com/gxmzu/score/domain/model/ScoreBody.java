package com.gxmzu.score.domain.model;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10
 * @Description: 打分请求模型
 */
public class ScoreBody {
    private Long matchId;
    private Long userId;
    private Long contestantId;
    private Double score;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getContestantId() {
        return contestantId;
    }

    public void setContestantId(Long contestantId) {
        this.contestantId = contestantId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
