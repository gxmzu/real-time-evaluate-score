package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/12/03
 * @Description: 验证用户权限服务
 */
@Service
public class VerifyUser {

    /**
     * 管理员接口
     * {@link com.gxmzu.score.domain.entity.User}
     */
    private static final List<String> ADMIN_PERMISSIONS = new ArrayList<String>() {{
        add("/config/list");
        add("/config/add");
        add("/user/list");
        add("/user/add");
    }};

    /**
     * 活动负责人接口
     * {@link com.gxmzu.score.domain.entity.User}
     */
    private static final List<String> PRINCIPAL_PERMISSIONS = new ArrayList<String>() {{
        add("/user/list");
        add("/user/generate");
    }};

    /**
     * 主评委接口
     * {@link com.gxmzu.score.domain.entity.User}
     */
    private static final List<String> LEAD_JUDGE_PERMISSIONS = new ArrayList<String>() {{
        add("/websocket/score");
    }};

    /**
     * 评委接口
     * {@link com.gxmzu.score.domain.entity.User}
     */
    private static final List<String> JUDGE_PERMISSIONS = new ArrayList<String>() {{
        add("/websocket/score");
    }};

    private static final Map<String, List<String>> USER_MAP = new HashMap<String, List<String>>() {{
        put(Constants.ADMIN, ADMIN_PERMISSIONS);
        put(Constants.PRINCIPAL, PRINCIPAL_PERMISSIONS);
        put(Constants.LEAD_JUDGE, LEAD_JUDGE_PERMISSIONS);
        put(Constants.JUDGE, JUDGE_PERMISSIONS);
    }};

    /**
     * 验证用户权限
     *
     * @param url  请求地址
     * @param user 用户
     * @return 返回true验证成功，返回false验证失败
     */
    public boolean verify(String url, User user) {
        String userType = user.getUserType();
        List<String> list = USER_MAP.get(userType);
        if (list != null && list.size() > 0) {
            return compareTo(list, url);
        } else {
            return false;
        }
    }

    /**
     * 从列表匹配url
     * 如果匹配不到，则去掉url最后一层/
     * 例如：请求/user/delete/3
     * 从列表匹配不到
     * 删除最后的/3，得到/user/delete
     * 从列表匹配到
     * 结束
     * @param list 列表
     * @param url url
     * @return 匹配结果
     */
    private boolean compareTo(List<String> list, String url) {
        if (list.contains(url)) {
            return true;
        }
        int index = url.lastIndexOf("/");
        String newUrl = url.substring(0, index);
        return list.contains(newUrl);
    }
}
