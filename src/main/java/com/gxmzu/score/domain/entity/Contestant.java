package com.gxmzu.score.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxmzu.score.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:10
 * @Description: 参赛者实体
 */
public class Contestant extends BaseEntity implements Serializable {

    /**
     * 参赛者id
     */
    private Long contestantId;

    /**
     * 比赛id
     */
    private Long matchId;

    /**
     * 参赛者名称
     */
    private String name;

    /**
     * 简介
     */
    private String info;

    /**
     * 比赛顺序
     */
    private Long matchOrder;

    /**
     * 是否开启评分通道
     */
    private String isOpen;

    /**
     * 得分，保留两位小数
     */
    private Double score;

    /**
     * 排名
     */
    private Long rank;

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

    public Long getContestantId() {
        return contestantId;
    }

    public void setContestantId(Long contestantId) {
        this.contestantId = contestantId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getMatchOrder() {
        return matchOrder;
    }

    public void setMatchOrder(Long matchOrder) {
        this.matchOrder = matchOrder;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
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
        return "Contestant{" +
                "contestantId=" + contestantId +
                ", matchId=" + matchId +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", matchOrder=" + matchOrder +
                ", isOpen='" + isOpen + '\'' +
                ", score=" + score +
                ", rank=" + rank +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
