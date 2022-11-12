package com.gxmzu.score.service;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Contestant;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:11
 * @Description: 参赛者服务
 */
public interface ContestantService {

    /**
     * 获取参赛者列表
     * @param contestant 参赛者查询信息
     *
     * @return 参赛者列表
     */
    public List<Contestant> getContestantList(Contestant contestant);


    /**
     * 随机排序参赛者的比赛顺序
     * @param matchId 比赛id
     * @return 操作结果 1代表排序成功，0代表排序失败
     */
    public List<Contestant> orderContestant(Long matchId);

    /**
     * 增加参赛者
     */
    int addContestant(Contestant contestant);
    /**
     * 删除参赛者
     */
    int deleteContestant(Long id);
    /**
     * 修改参赛者
     */
    int updateContestant(Contestant contestant);


}
