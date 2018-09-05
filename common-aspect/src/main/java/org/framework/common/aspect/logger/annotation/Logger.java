package org.framework.common.aspect.logger.annotation;

import java.lang.annotation.*;

/**
 * @Author xiejs
 * @Description 打印日志注解
 * @Date Created in 2018/9/3 17:34
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logger {
}
