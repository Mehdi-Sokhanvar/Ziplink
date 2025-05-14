package com.ziplink.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    @Before("execution(* com.ziplink.service.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info(BLUE + "Executing " + methodName + " in class " + joinPoint.getSignature().getDeclaringTypeName() + RESET);
    }
}
