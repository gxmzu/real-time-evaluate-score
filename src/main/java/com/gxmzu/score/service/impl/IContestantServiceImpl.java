package com.gxmzu.score.service.impl;

import com.gxmzu.score.domain.entity.Contestant;
import com.gxmzu.score.mapper.ContestantMapper;
import com.gxmzu.score.service.IContestantService;
import com.gxmzu.score.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/10:12
 * @Description: 参赛者服务实现类
 */
@Service
public class IContestantServiceImpl implements IContestantService {

    @Resource
    private ContestantMapper contestantMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    public List<Contestant> getContestantList(Contestant contestant) {
        return contestantMapper.selectContestantList(contestant);
    }

}
