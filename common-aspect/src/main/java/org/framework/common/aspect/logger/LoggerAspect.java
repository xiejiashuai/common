package org.framework.common.aspect.logger;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author xiejs
 * @Description 打印日志切面
 * @Date Created in 2018/9/3 17:33
 */
@Component
@Aspect
public class LoggerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("@annotation(org.framework.common.aspect.logger.annotation.Logger)")
    public void logPointcut() {

    }

    @Around("logPointcut()")
    public Object doSurround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] objects = proceedingJoinPoint.getArgs();
        String name = proceedingJoinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        
        LOGGER.info("class: {}, function name: {}", proceedingJoinPoint.getTarget().getClass().getName(), name);
        LOGGER.info("in args: <{}>", JSON.toJSONString(objects));
        Object result =null;
        try {
            result = proceedingJoinPoint.proceed();
        }catch (Throwable t){
            LOGGER.error("invoke method:{} occur exception:{}",name,t.getMessage());
            throw t;
        }

        LOGGER.info("out args: <{}>", JSON.toJSONString(result));
        LOGGER.info("execute time: {}", System.currentTimeMillis() - startTime);

        return result;
    }


}
