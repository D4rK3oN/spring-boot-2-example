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
    private static final String SPRING_DATA_REPOSITORY_MANAGER = "execution(* org.springframework.data.repository.*Repository.*(..))";

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

    @Pointcut(SPRING_DATA_REPOSITORY_MANAGER)
    public void logSpringDataRepositoryLayer() {
    }

    /**
     * Logging all app layer.
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("logControllerLayer() || logServiceLayer() || logRepositoryLayer()")
    public Object printTraces(final ProceedingJoinPoint joinPoint) throws Throwable {
        printInTrace(joinPoint);

        final Object resultMethodExecution = joinPoint.proceed();

        printOutTrace(joinPoint, resultMethodExecution);

        return resultMethodExecution;
    }

    /**
     * Logging Spring Data Repository layer.
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("logSpringDataRepositoryLayer()")
    public Object printSpringDataTraces(final ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Invoked class [{}.{}] | Input Args [{}]",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                List.of(joinPoint.getArgs()).isEmpty() ? "EMPTY" : joinPoint.getArgs());

        final Object resultMethodExecution = joinPoint.proceed();

        logger.info("Invoked class [{}.{}] | Output Response [{}]",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                resultMethodExecution);

        return resultMethodExecution;
    }

    /**
     * Print IN trace.
     *
     * @param joinPoint
     */
    private void printInTrace(final ProceedingJoinPoint joinPoint) {
        logger.info("Invoked class [{}.{}] | Input Args [{}]",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                List.of(joinPoint.getArgs()).isEmpty() ? "EMPTY" : joinPoint.getArgs());
    }

    /**
     * Print OUT trace.
     *
     * @param joinPoint
     * @param resultMethodExecution
     */
    private void printOutTrace(final ProceedingJoinPoint joinPoint, final Object resultMethodExecution) {
        logger.info("Invoked class [{}.{}] | Output Response [{}]",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                resultMethodExecution);
    }
}
