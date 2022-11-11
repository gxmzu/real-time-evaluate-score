package com.gxmzu.score.controller;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.model.ChannelBody;
import com.gxmzu.score.domain.model.ScoreBody;
import com.gxmzu.score.service.impl.ScoreServiceImpl;
import com.gxmzu.score.utils.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AjaxResult scoreTheContestants(HttpServletRequest httpServletRequest, @RequestBody ScoreBody scoreBody) {
        if ((scoreBody.getMatchId() == null) || (scoreBody.getContestantId() == null)
                || (scoreBody.getUserId() == null) || scoreBody.getScore() < 0)
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数!");

        return scoreService.scoreTheContestants(httpServletRequest,scoreBody.getMatchId(), scoreBody.getUserId(),
                scoreBody.getContestantId(), scoreBody.getScore());
    }


    /**
     * 开启评分通道
     */
    @PostMapping("/openChannel")
    public AjaxResult openChannel(@RequestBody ChannelBody channelBody) {
        if ((channelBody.getMatchId() == null) || (channelBody.getContestantId() == null))
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        return scoreService.openChannel(channelBody.getMatchId(), channelBody.getContestantId());
    }

    /**
     * 关闭打分通道
     */
    @PostMapping("/closeChannel")
    public AjaxResult closeChannel(@RequestBody ChannelBody channelBody){
        if ((channelBody.getMatchId() == null) || (channelBody.getContestantId() == null))
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        return null;
    }
}
