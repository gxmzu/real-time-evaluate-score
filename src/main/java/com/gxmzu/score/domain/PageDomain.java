package com.gxmzu.score.domain;


import java.io.Serializable;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/16:39
 * @Description: 分页参数对象
 */
public class PageDomain implements Serializable {

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;

    /**
     * 每页显示记录数
     */
    private Integer pageSize;

    /**
     * 分页参数合理化
     */
    private Boolean reasonable = true;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getReasonable() {
        return reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }
}

