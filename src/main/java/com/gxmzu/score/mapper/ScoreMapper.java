package com.gxmzu.score.mapper;

import com.gxmzu.score.domain.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/11
 * @Description: 评分数据层
 */

@Mapper
public interface ScoreMapper {

    /**
     * 获取某场比赛的评分信息
     *
     * @param matchId 比赛id
     * @return 评分信息
     */
    public List<Score> selectScoreList(Long matchId);

}
