package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.Contestant;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:11
 * @Description: 参赛者服务
 */
public interface IContestantService {

    /**
     * 获取参赛者列表
     * @param contestant 参赛者查询信息
     *
     * @return 参赛者列表
     */
    public List<Contestant> getContestantList(Contestant contestant);

}
