package com.shape.shapetest.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Around("@annotation(com.shape.shapetest.annotation.Log)")
    public Object logExecution(ProceedingJoinPoint pjp) throws Throwable {
        final Logger logger = LoggerFactory.getLogger(pjp.getSourceLocation().getWithinType());
        String method = pjp.getSourceLocation().getWithinType().getSimpleName() + "." + pjp.getSignature().getName();

        Object[] args = pjp.getArgs();
        logger.info("stage=init method={} args={}", method, args);

        Object proceed = pjp.proceed();

        if (Objects.nonNull(proceed))
            logger.info("stage=end method={} argsIn={} argsOut={}", method, args, proceed);
        else
            logger.info("stage=end method={} argsIn={}", method, args);

        return proceed;
    }

}
