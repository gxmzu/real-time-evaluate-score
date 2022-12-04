package com.gxmzu.score.service.impl;

import com.gxmzu.score.domain.entity.Config;
import com.gxmzu.score.mapper.ConfigMapper;
import com.gxmzu.score.service.IConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/12/04
 * @Description: 系统配置服务实现类
 */
@Service
public class IConfigServiceImpl implements IConfigService {

    @Resource
    private ConfigMapper configMapper;

    @Override
    public List<Config> getConfigList(Config config) {
        return configMapper.selectConfigList(config);
    }
}
