package com.gxmzu.score.controller;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Config;
import com.gxmzu.score.domain.entity.Contestant;
import com.gxmzu.score.service.IConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/12/04
 * @Description: 系统配置接口
 */
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Resource
    private IConfigService configService;

    /**
     * 获取系统配置列表
     *
     * @param config 系统配置查询信息
     * @return 系统配置列表
     */
    @GetMapping("/list")
    public AjaxResult getContestantList(Config config) {
        startPage(config);
        List<Config> list = configService.getConfigList(config);
        return getDataTable(list);
    }
}
