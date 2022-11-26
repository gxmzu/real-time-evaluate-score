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
     *
     * @param contestant 参赛者查询信息
     * @return 参赛者列表
     */
    public List<Contestant> selectContestantList(Contestant contestant);

}
