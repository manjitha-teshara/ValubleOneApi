package com.valuable.valuable._one.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.valuable.homeloan.controller..*(..))")
    public Object logExecution(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time = System.currentTimeMillis() - start;

        log.info("{} executed in {} ms",
                pjp.getSignature().toShortString(), time);

        return result;
    }
}

