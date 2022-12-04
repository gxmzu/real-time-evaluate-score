package com.gxmzu.score.service.impl;

import com.gxmzu.score.domain.entity.UserMatch;
import com.gxmzu.score.mapper.UserMatchMapper;
import com.gxmzu.score.service.IUserMatchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/12/04
 * @Description: 用户比赛服务实现类
 */
@Service
public class IUserMatchServiceImpl implements IUserMatchService {

    @Resource
    private UserMatchMapper userMatchMapper;

    @Override
    public List<UserMatch> getUserMatchList(UserMatch userMatch) {
        return userMatchMapper.selectUserMatchList(userMatch);
    }

    @Override
    public UserMatch getUserMatch(UserMatch userMatch) {
        return userMatchMapper.selectUserMatch(userMatch);
    }
}
