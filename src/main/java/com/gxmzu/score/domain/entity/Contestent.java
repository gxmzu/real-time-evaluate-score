package com.gxmzu.score.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxmzu.score.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:10
 * @Description: 参赛对象实体
 */
public class Contestent extends BaseEntity implements Serializable {

    /**
     * 参赛对象id
     */
    private long contestentId;

    /**
     * 比赛id
     */
    private long matchId;

    /**
     * 参赛对象名称
     */
    private String name;

    /**
     * 简介
     */
    private String info;

    /**
     * 比赛顺序
     */
    private long matchOrder;

    /**
     * 是否开启评分通道
     */
    private String isOpen;

    /**
     * 得分，保留两位小数
     */
    private double score;

    /**
     * 排名
     */
    private long rank;

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

    public long getContestentId() {
        return contestentId;
    }

    public void setContestentId(long contestentId) {
        this.contestentId = contestentId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
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

    public long getMatchOrder() {
        return matchOrder;
    }

    public void setMatchOrder(long matchOrder) {
        this.matchOrder = matchOrder;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
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
        return "contestent{" +
                "contestentId=" + contestentId +
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
