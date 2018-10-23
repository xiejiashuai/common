package org.framework.common.util.reflect;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

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
    
     /**
     * 获取对象中属性值为null的属性名称集合
     */
    public static <T> String[] findIgnoreProperties(T t) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        // 不需要覆盖的属性集合
        List<String> ignoreProperties = new ArrayList<>();

        BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());

        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {

            if (propertyDescriptor.getPropertyType().equals(Class.class)) {
                continue;
            }

            Method readMethod = propertyDescriptor.getReadMethod();

            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                readMethod.setAccessible(true);
            }

            // 获取属性值
            Object propertyValue = readMethod.invoke(t, null);

            if (null == propertyValue) {
                // 不需要覆盖的属性名称
                ignoreProperties.add(propertyDescriptor.getName());
            }
        }

        return ignoreProperties.toArray(new String[ignoreProperties.size()]);

    }

    /**
     * 从source对象中覆盖target中的值
     *
     * @param source           源
     * @param target           目标对象
     * @param ignoreProperties 不需要覆盖的属性
     * @param <T>
     */
    public static <T> void overrideProperties(T source, T target, String... ignoreProperties) {
        // 进行覆盖
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * source对象覆盖target对象中的值
     * <note>source对象中属性值为null的不会覆盖target对象</note>
     *
     * @param source 源
     * @param target 目标对象
     * @param <T>
     */
    public static <T> void overridePropertiesIgnoreNullProperties(T source, T target) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        String[] ignoreProperties = findIgnoreProperties(source);

        // 进行覆盖
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }


    private ReflectUtils() {
        throw new RuntimeException("can not be constructor");
    }

}
