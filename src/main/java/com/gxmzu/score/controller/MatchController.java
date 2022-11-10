package com.gxmzu.score.controller;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Match;
import com.gxmzu.score.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:35
 * @Description: 比赛接口
 */
@RestController
@RequestMapping("/match")
public class MatchController extends BaseController{

    @Autowired
    private MatchService matchService;

    /**
     * 获取比赛列表
     *
     * @param match 比赛查询信息
     * @return 比赛列表
     */
    @GetMapping("/list")
    public AjaxResult getMatchServiceList(Match match) {
        startPage(match);
        List<Match> list = matchService.getMatchList(match);
        return getDataTable(list);
    }
}
