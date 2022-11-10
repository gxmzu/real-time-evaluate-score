package com.gxmzu.score.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.PageDomain;
import com.gxmzu.score.domain.TableDataInfo;
import com.gxmzu.score.utils.StringUtils;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:19
 * @Description: 分页接口
 */
public class BaseController {


    /**
     * 设置请求分页数据
     */
    protected void startPage(PageDomain pageDomain) {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Boolean reasonable = pageDomain.getReasonable();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)){
            PageHelper.startPage(pageNum, pageSize).setReasonable(reasonable);
        }
    }

    /**
     * 响应请求分页数据
     */
    protected AjaxResult getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return AjaxResult.success(rspData);
    }

}
