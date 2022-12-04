package com.gxmzu.score.mapper;

import com.gxmzu.score.domain.entity.UserMatch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/12/04
 * @Description:
 */
@Mapper
public interface UserMatchMapper {

    /**
     * 获取用户比赛列表
     *
     * @param userMatch 用户比赛信息
     * @return 用户比赛列表
     */
    public List<UserMatch> selectUserMatchList(UserMatch userMatch);

    /**
     * 获取用户比赛对象
     *
     * @param userMatch 用户比赛信息
     * @return 用户比赛对象
     */
    public UserMatch selectUserMatch(UserMatch userMatch);
}
