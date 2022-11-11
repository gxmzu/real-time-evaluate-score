package com.gxmzu.score.mapper;

import com.gxmzu.score.domain.entity.Contestant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:13
 * @Description: 参赛者数据层
 */
@Mapper
public interface ContestantMapper {

    /**
     * 获取参赛者列表
     * @param contestant 参赛者查询信息
     * @return 参赛者列表
     */
    public List<Contestant> selectContestantList(Contestant contestant);


    /**
     * 获取指定比赛的所有参赛者
     * @param contestant 比赛id
     * @return 参赛者列表
     */
    public List<Contestant> selectContestantListByMatchId(Contestant contestant);

    /**
     * 批量更新参赛者
     * @param contestantList 参赛者列表
     * @return 更新结果
     */
    public int batchUpdateContestant(List<Contestant> contestantList);
}
