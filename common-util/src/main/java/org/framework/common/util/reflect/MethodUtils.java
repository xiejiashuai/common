package org.framework.common.util.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public final class MethodUtils {

    private MethodUtils() {
        throw new RuntimeException("can not be constructor");
    }

    /**
     * 判定是否为静态方法
     *
     * @param method
     * @return
     */
    public static boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * 在给定类中找到具有相同方法签名的方法
     *
     * @param mustStatic     相同方法签名的方法是否必须是静态的
     * @param clazz          给定类
     * @param name           方法名称
     * @param returnType     返回值类型
     * @param parameterTypes 参数类型列表
     * @return
     */
    public static Method findMethod(boolean mustStatic, Class<?> clazz, String name, Class<?> returnType, Class<?>... parameterTypes) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (name.equals(method.getName()) && checkStatic(mustStatic, method)
                    && returnType.isAssignableFrom(method.getReturnType())
                    && Arrays.equals(parameterTypes, method.getParameterTypes())) {

                return method;
            }
        }
        // 从父类中寻找
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && !Object.class.equals(superClass)) {
            return findMethod(mustStatic, superClass, name, returnType, parameterTypes);
        } else {
            return null;
        }
    }

    /**
     * Get declared method with provided name and parameterTypes in given class and its super classes.
     * All parameters should be valid.
     *
     * @param clazz          class where the method is located
     * @param name           method name
     * @param parameterTypes method parameter type list
     * @return resolved method, null if not found
     */
    public static Method getDeclaredMethodFor(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethodFor(superClass, name, parameterTypes);
            }
        }
        return null;
    }

    private static boolean checkStatic(boolean mustStatic, Method method) {
        return !mustStatic || isStatic(method);
    }


}
