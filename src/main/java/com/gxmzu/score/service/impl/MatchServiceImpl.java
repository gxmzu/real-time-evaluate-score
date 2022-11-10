package com.gxmzu.score.service.impl;

import com.gxmzu.score.domain.entity.Match;
import com.gxmzu.score.mapper.MatchMapper;
import com.gxmzu.score.service.MatchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:39
 * @Description: 比赛服务实现类
 */
@Service
public class MatchServiceImpl implements MatchService {

    @Resource
    private MatchMapper matchMapper;

    @Override
    public List<Match> getMatchList(Match match) {
        return matchMapper.selectMatchList(match);
    }
}
