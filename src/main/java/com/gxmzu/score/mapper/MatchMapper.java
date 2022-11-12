package com.gxmzu.score.mapper;

import com.gxmzu.score.domain.entity.Match;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:40
 * @Description: 比赛数据层
 */
@Mapper
public interface MatchMapper {

    /**
     * 获取比赛列表
     * @param match 比赛查询信息
     *
     * @return 比赛列表
     */
    public List<Match> selectMatchList(Match match);

    /**
     * 添加比赛信息
     *
     * @param match
     * @return 返回自增id
     */
    public int addMatch(Match match);

    /**
     * 修改比赛信息
     *
     * @param match
     * @return
     */
    public int updateMatch(Match match);

    /**
     * 删除比赛信息
     *
     * @param matchId 比赛id
     * @return
     */
    public int deleteMatch(@Param("matchId") Integer matchId);
}
