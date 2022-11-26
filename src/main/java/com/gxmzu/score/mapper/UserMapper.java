package com.gxmzu.score.mapper;

import com.gxmzu.score.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/17:25
 * @Description: 用户数据层
 */
@Mapper
public interface UserMapper {

    /**
     * 获取用户列表
     * @param user 用户信息
     * @return 用户列表
     */
    public List<User> selectUserList(User user);

    /**
     * 查询用户
     *
     * @param user 用户信息
     * @return 用户信息
     */
    public User selectUserByUserName(User user);

}
