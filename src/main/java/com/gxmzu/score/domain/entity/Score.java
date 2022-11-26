package com.gxmzu.score.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxmzu.score.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:14
 * @Description: 评分实体
 */
public class Score extends BaseEntity implements Serializable {

    /**
     * 比赛id
     */
    private Long matchId;

    /**
     * 参赛者id
     */
    private Long contestantId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 回合
     */
    private int turn;

    /**
     * 得分
     */
    private Double score;

    /**
     * 进度
     */
    private String progress;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Score{" +
                "matchId=" + matchId +
                ", contestantId=" + contestantId +
                ", userId=" + userId +
                ", turn=" + turn +
                ", score=" + score +
                ", progress='" + progress + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
