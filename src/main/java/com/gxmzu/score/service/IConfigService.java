package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.Config;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/12/04
 * @Description: 系统配置服务
 */
public interface IConfigService {

    /**
     * 获取系统配置列表
     *
     * @param config 系统配置查询信息
     *
     * @return 系统配置列表
     */
    List<Config> getConfigList(Config config);
}
