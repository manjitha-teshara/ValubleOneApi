package com.valuable.valuable._one.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    @Around("execution(* com.valuable.valuable._one.controller..*(..))")
    public Object logControllerExecution(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();
        String method = pjp.getSignature().toShortString();

        try {
            Object result = pjp.proceed();
            long time = System.currentTimeMillis() - start;

            log.info("API {} completed in {} ms", method, time);
            return result;

        } catch (Exception ex) {
            long time = System.currentTimeMillis() - start;

            log.error("API {} failed after {} ms : {}",
                    method, time, ex.getMessage(), ex);
            throw ex;
        }
    }
}

