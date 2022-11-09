package com.gxmzu.score.domain;


import com.gxmzu.score.utils.StringUtils;

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
     * 排序的方向desc或者asc
     */
    private String orderBy = "asc";

    /**
     * 分页参数合理化
     */
    private Boolean reasonable = true;

    /**
     * 升序
     */
    private static final String ASC = "asc";

    /**
     * 降序
     */
    private static final String DESC = "desc";

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

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

    /**
     * 验证分页参数
     */
    public void checkOrderQuery() {
        if (StringUtils.isNull(this.pageNum)) {
            this.pageNum = 1;
        }
        if (StringUtils.isNull(this.pageSize)) {
            this.pageSize = 10;
        }
        if (!ASC.equals(this.orderBy) && !DESC.equals(this.orderBy)) {
            this.orderBy = ASC;
        }
    }
}

