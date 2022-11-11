package com.gxmzu.score.service;

import com.gxmzu.score.domain.AjaxResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10
 * @Description: 打分模块
 */
public interface ScoreService {
    AjaxResult scoreTheContestants(HttpServletRequest httpServletRequest, Long matchId, Long userId, Long contestantId, Double score);

    AjaxResult openChannel(Long matchId, Long contestantId);

    AjaxResult closeChannel(Long matchIdm, Long contestantId);
}
