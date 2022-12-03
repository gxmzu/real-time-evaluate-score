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

    private static final List<String> ADMIN_PERMISSIONS = new ArrayList<String>() {{
        add("/user/list");
    }};

    private static final List<String> PRINCIPAL_PERMISSIONS = new ArrayList<String>() {{
        add("/user/list");
    }};

    private static final List<String> LEAD_JUDGE_PERMISSIONS = new ArrayList<String>() {{
    }};

    private static final List<String> JUDGE_PERMISSIONS = new ArrayList<String>() {{
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
            return list.contains(url);
        } else {
            return false;
        }
    }
}
