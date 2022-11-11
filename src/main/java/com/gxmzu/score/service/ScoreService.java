package com.gxmzu.score.service;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Score;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10
 * @Description: 打分模块
 */
public interface ScoreService {
    AjaxResult scoreTheContestants(HttpServletRequest httpServletRequest, Score score);

    AjaxResult openChannel(HttpServletRequest httpServletRequest, Long matchId, Long contestantId);

    AjaxResult closeChannel(HttpServletRequest httpServletRequest, Long matchIdm, Long contestantId);

    AjaxResult ScoreList(Long matchId);

    AjaxResult detailScoring(Long matchId, Long contestantId);
}
