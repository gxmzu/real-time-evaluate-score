package com.gxmzu.score.mapper;

import com.gxmzu.score.domain.entity.Score;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/11
 * @Description:
 */

@Mapper
public interface ScoreMapper {
    int saveScore(Score score);

    int openChannel(Long matchId, Long contestantId);

    /**
     * 查询下一个参赛队伍
     * @param matchId
     * @param order
     * @return
     */
    Score queryNext(Long matchId, Long order);
}
