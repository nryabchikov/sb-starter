package ru.clevertec.sb.starter.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.sb.starter.config.PerformanceMonitorProperties;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class PerformanceMonitorAspect {
    private final PerformanceMonitorProperties properties;

    @Pointcut("@annotation(ru.clevertec.sb.starter.aop.MonitorPerformance)")
    public void hasMonitorPerformance() {
    }

    @Around("hasMonitorPerformance()")
    public Object monitorExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        if (properties.isPerformanceEnabled()) {
            long startTime = System.currentTimeMillis();
            result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            if (executionTime > properties.getMinTime()) {
                log.info("Method [{}] executed in [{} ms]", joinPoint.getSignature(), executionTime);
            }
            return result;
        } else {
            return null;
        }
    }
}
