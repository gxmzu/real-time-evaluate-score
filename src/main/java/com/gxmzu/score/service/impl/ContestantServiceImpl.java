package com.gxmzu.score.service.impl;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Contestant;
import com.gxmzu.score.mapper.ContestantMapper;
import com.gxmzu.score.service.ContestantService;
import com.gxmzu.score.utils.HttpStatus;
import com.gxmzu.score.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:12
 * @Description: 参赛者服务实现类
 */
@Service
public class ContestantServiceImpl implements ContestantService {

    @Resource
    private ContestantMapper contestantMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    public List<Contestant> getContestantList(Contestant contestant) {
        return contestantMapper.selectContestantList(contestant);
    }

    @Override
    public List<Contestant> orderContestant(Long matchId) {
        Contestant contestant = new Contestant();
        contestant.setMatchId(matchId);
        List<Contestant> contestantList = contestantMapper.selectContestantListByMatchId(contestant);
        Collections.shuffle(contestantList);
        for (int i = 0; i < contestantList.size(); i++) {
            Contestant c = contestantList.get(i);
            c.setMatchOrder(i + 1);
            contestantList.set(i, c);
        }
        redisCache.setCacheObject("match:" + matchId + ":isScoring", contestantList.get(0));
        contestantMapper.batchUpdateContestant(contestantList);
        return contestantList;
    }

    @Override
    public int addContestant(Contestant contestant) {
        if (contestant.getMatchId() <= 0 || contestant.getName() == null
                || contestant.getInfo() == null || contestant.getMatchOrder() <= 0){//必填项不能为空
            return -1;
        }
        int item = contestantMapper.addContestant(contestant);
        return item;
    }

    @Override
    public int deleteContestant(Long id) {
        int item = contestantMapper.deleteContestant(id);
        if (item <= 0){
            return -1;
        }
        return item;
    }

    @Override
    public int updateContestant(Contestant contestant) {
        if (contestant.getMatchId() <= 0 || contestant.getName() == null
                || contestant.getInfo() == null || contestant.getMatchOrder() <= 0){
            return -1;
        }
        int item = contestantMapper.updateContestant(contestant);
        return item;
    }


}
