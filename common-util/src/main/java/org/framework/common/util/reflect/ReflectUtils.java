package org.framework.common.util.reflect;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @Date Created in 2018/8/27 19:44
 * @see ReflectionUtils
 * @see ClassUtils
 */
public final class ReflectUtils {

    /**
     * 获取制定实例中的字段的值
     */
    public static <T> T getFieldValue(Class<?> clazz, Object instance, String fieldName) {

        Field field = ReflectionUtils.findField(clazz, fieldName);

        ReflectionUtils.makeAccessible(field);

        return (T) ReflectionUtils.getField(field, instance);

    }


    private ReflectUtils() {
        throw new RuntimeException("can not be constructor");
    }

}
