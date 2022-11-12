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
     * @return 用户列表
     */
    public List<User> selectUserList(User user);

    /**
     * 查询用户
     * @param user 用户信息
     * @return 用户信息
     */
    public User selectUserByUserName(User user);

    /**
     * 重置密码
     * @return 重置结果
     */
    public int resetPassword(User user);

    /**
     * 删除用户
     * @param userId 用户id
     * @return 删除结果
     */
    public int deleteUser(Long userId);

    /**
     * 随机生成用户
     * @param number 生成用户数量，范围在1~200之间
     * @return 生成结果
     */
    public List<User> randomGenerateUser(int number);

    /**
     * 修改用户
     * @param user 修改后的用户信息
     * @return 修改结果
     */
    public int updateUser(User user);

    /**
     * 保存用户
     * @param user
     * @return 保存结果
     */
    public int addUser(User user);
}
