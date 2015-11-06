package com.renfei.blog.common.utils;

import java.text.DecimalFormat;

import static com.renfei.blog.common.utils.Arguments.isNull;


/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-6-4
 */
public class NumberUtils {

    /**
     * 2位小数
     */
    public static final DecimalFormat DECIMAL_FMT_2 = new DecimalFormat("0.00");

    /**
     * 格式化价格(两位小数)
     * @param price 价格
     * @return
     */
    public static String formatPrice(Number price) {
        if (price == null) {
            return "";
        }
        return DECIMAL_FMT_2.format(price.doubleValue() / 100);
    }

    /**
     * 除法(四舍五入)
     * @param a 被除数
     * @param b 除数
     * @param scale 保留小数位
     * @return 结果字符串
     */
    public static String divide(Long a, Long b, int scale){
        if (isNull(a) || isNull(b)){
            return "";
        }
        Object result = (double)a / (double)b;
        return String.format("%."+ scale + "f", result);
    }

    /**
     * 除法(四舍五入)
     * @param a 被除数
     * @param b 除数
     * @param scale 保留小数位
     * @return 结果字符串
     */
    public static String divide(Integer a, Integer b, int scale){
        return divide(a, b, scale, 1);
    }

    /**
     * 除法(四舍五入)
     * @param a 被除数
     * @param b 除数
     * @param scale 保留小数位
     * @param factor 乘法因子, 如10, 100, ..
     * @return 结果字符串
     */
    public static String divide(Integer a, Integer b, int scale, int factor){
        if (isNull(a) || isNull(b)){
            return "";
        }
        Object result = ((double)a / (double)b) * factor;
        return String.format("%."+ scale + "f", result);
    }
}
