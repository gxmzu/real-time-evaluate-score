package com.gxmzu.score.utils.uuid;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/0:12
 * @Description: ID生成器工具类
 */
public class IdUtils {
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 生成16位订单号
     * 生成规则
     * 14位时间位数 + 15-16位用户ID的倒数1-2位
     * 例如：
     * 20211122200854 + 02
     *
     * @param userId 用户id
     * @return 16位订单号
     */
    public static String generateOrderID(Long userId) {
        return getTime().replaceAll("[[\\s-:punct:]]", "") + frontCompWithZore(userId);
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss时间格式
     *
     * @return 时间
     */
    public static String getTime() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdfTime.format(new Date());
    }

    /**
     * 截取用户id后两位，不足两位的前面补0
     *
     * @return 生成六位数验证码
     */
    public static String frontCompWithZore(Long userId) {
        String s = String.format("%02d", userId);
        return s.substring(s.length() - 2);
    }

}
