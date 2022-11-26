package com.gxmzu.score.controller;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Score;
import com.gxmzu.score.service.IScoreService;
import com.gxmzu.score.utils.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10
 * @Description: 评分接口
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Resource
    IScoreService scoreService;

    @GetMapping("/list")
    public AjaxResult list(@RequestParam Long matchId) {
        if (matchId == null || matchId == 0L) {
            return AjaxResult.error(HttpStatus.LACK_QUERY,"缺少参数");
        }
        return AjaxResult.success(scoreService.getScoreList(matchId));
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody Score score) {
        return AjaxResult.success();
    }
}
