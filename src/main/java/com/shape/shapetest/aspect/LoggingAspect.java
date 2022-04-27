package com.shape.shapetest.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Around("@annotation(com.shape.shapetest.annotation.Log)")
    public Object logExecution(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("stage=init " + pjp.getSignature().getName());
        Object proceed = pjp.proceed();
        logger.info("stage=end " + pjp.getSignature().getName());
        return proceed;
    }

}
