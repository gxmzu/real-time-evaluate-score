package com.gxmzu.score.mapper;

import com.gxmzu.score.domain.entity.Config;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/12/04
 * @Description: 系统配置数据层
 */

@Mapper
public interface ConfigMapper {

    /**
     * 获取系统配置列表
     *
     * @param config 系统配置查询信息
     * @return 系统配置列表
     */
    public List<Config> selectConfigList(Config config);
}
