package com.gxmzu.score.utils;

import com.gxmzu.score.domain.entity.Score;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/11
 * @Description:
 */
public class CalculateScoreUtils {

    /**
     * 修剪平均分（去掉最高最低）
     * @param list
     * @return
     */
    public static Double trimMean(List<Score> list) {

        return null;
    }

    /**
     * 总分
     * @param list
     * @return
     */
    public static double totalScore(List<Score> list) {
        double result = 0;

        for (Score o : list) {
            result += o.getScore();
        }

        return CalculateScoreUtils.doubleFormat(result);
    }

    /**
     * 平均分
     * @param list
     * @return
     */
    public static double averageScore(List<Score> list){
        double result = 0;

        for (Score o : list) {
            result += o.getScore();
        }

        return doubleFormat(result/list.size());
    }

    /**
     * 保留两位小鼠
     * @param value
     * @return
     */
    private static double doubleFormat(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String format = df.format(value);

        return Double.parseDouble(format);
    }
}
