package com.gxmzu.score.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxmzu.score.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:01
 * @Description: 比赛实体
 */
public class Match extends BaseEntity implements Serializable {

    /**
     * 比赛id
     */
    private long matchId;

    /**
     * 比赛名称
     */
    private String matchName;

    /**
     * 比赛简介
     */
    private String info;

    /**
     * 分制
     */
    private double maxScore;

    /**
     * 评分规则
     */
    private String scoreRuleName;

    /**
     * 用户列表
     */
    private User[] userList;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public String getScoreRuleName() {
        return scoreRuleName;
    }

    public void setScoreRuleName(String scoreRuleName) {
        this.scoreRuleName = scoreRuleName;
    }

    public User[] getUserList() {
        return userList;
    }

    public void setUserList(User[] userList) {
        this.userList = userList;
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
        return "match{" +
                "matchId=" + matchId +
                ", matchName='" + matchName + '\'' +
                ", info='" + info + '\'' +
                ", maxScore=" + maxScore +
                ", scoreRuleName='" + scoreRuleName + '\'' +
                ", userList=" + Arrays.toString(userList) +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
