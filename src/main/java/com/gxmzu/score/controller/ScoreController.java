package com.gxmzu.score.controller;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Score;
import com.gxmzu.score.domain.model.ChannelBody;
import com.gxmzu.score.domain.model.ScoreBody;
import com.gxmzu.score.service.impl.ScoreServiceImpl;
import com.gxmzu.score.utils.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10
 * @Description: 评分接口
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Resource
    ScoreServiceImpl scoreService;

    /**
     * 给选手打分
     */
    @PostMapping("/add")
    public AjaxResult scoreTheContestants(HttpServletRequest httpServletRequest, @RequestBody Score score) {
        if ((score.getMatchId() == null) || (score.getContestantId() == null)
                || (score.getUserId() == null) || score.getScore() < 0) {
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数!");
        }

        return scoreService.scoreTheContestants(httpServletRequest,score);
    }


    /**
     * 开启评分通道
     */
    @PostMapping("/openChannel")
    public AjaxResult openChannel(HttpServletRequest httpServletRequest, @RequestBody ChannelBody channelBody) {
        if ((channelBody.getMatchId() == null) || (channelBody.getContestantId() == null)) {
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        }
        return scoreService.openChannel(httpServletRequest, channelBody.getMatchId(), channelBody.getContestantId());
    }

    /**
     * 关闭打分通道
     */
    @PostMapping("/closeChannel")
    public AjaxResult closeChannel(HttpServletRequest httpServletRequest, @RequestBody ChannelBody channelBody){
        if ((channelBody.getMatchId() == null) || (channelBody.getContestantId() == null)) {
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        }
        return scoreService.closeChannel(httpServletRequest, channelBody.getMatchId(), channelBody.getContestantId());
    }

    /**
     * 评分列表
     */
    @GetMapping("/list")
    public AjaxResult rankList(@RequestBody Long matchId) {
        if (matchId == null) {
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        }
        return scoreService.ScoreList(matchId);
    }

    /**
     * 评分详细
     */
    @GetMapping("/detailScoring")
    public AjaxResult detailScoring(@RequestBody ChannelBody channelBody) {
        if (channelBody.getContestantId() == null || channelBody.getMatchId() == null) {
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        }
        return scoreService.detailScoring(channelBody.getMatchId(), channelBody.getContestantId());
    }
}
