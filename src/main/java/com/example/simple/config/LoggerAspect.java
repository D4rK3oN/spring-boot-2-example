package com.example.simple.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class LoggerAspect {
    private static final String CONTROLLER_MANAGER = "execution(* com.example.*.web.*Controller.*(..))";
    private static final String SERVICE_MANAGER = "execution(* com.example.*.service.*Service.*(..))";
    private static final String REPOSITORY_MANAGER = "execution(* com.example.*.repository.*Repository.*(..))";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut(CONTROLLER_MANAGER)
    public void logControllerLayer() {
    }

    @Pointcut(SERVICE_MANAGER)
    public void logServiceLayer() {
    }

    @Pointcut(REPOSITORY_MANAGER)
    public void logRepositoryLayer() {
    }

    @Around("logControllerLayer() || logServiceLayer() || logRepositoryLayer()")
    public Object printTraces(final ProceedingJoinPoint joinPoint) throws Throwable {
        printInTrace(joinPoint);

        final Object resultMethodExecution = joinPoint.proceed();

        printOutTrace(joinPoint, resultMethodExecution);

        return resultMethodExecution;
    }

    private void printInTrace(final ProceedingJoinPoint joinPoint) {
        logger.info("Invoked class [{}.{}] | Input Args [{}]",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                List.of(joinPoint.getArgs()).isEmpty() ? "EMPTY" : joinPoint.getArgs());
    }

    private void printOutTrace(final ProceedingJoinPoint joinPoint, final Object resultMethodExecution) {
        logger.info("Invoked class [{}.{}] | Output Response [{}]",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                resultMethodExecution);
    }
}
