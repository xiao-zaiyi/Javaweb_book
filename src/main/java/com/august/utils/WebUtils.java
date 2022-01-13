package com.august.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2021-12-30   21:40
 */
public class WebUtils {
    /**
     * 把 Map 的值注入到javaBean中
     *
     * @param value
     * @param bean
     * @return
     */

    public static <T> T copyParametersToBean(Map value, T bean) {
        try {
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }

    public static int parseInt(String StrInt, int defaultValue) {
        if(StrInt == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(StrInt);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

}
