package com.github.memoryh.nutritionfacts.api.logtrace.aop;

import com.github.memoryh.nutritionfacts.api.logtrace.LogTrace;
import com.github.memoryh.nutritionfacts.api.logtrace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    // com.github.memoryh.nutritionfacts.api.controller 패키지 및 그 하위 패키지에 있는 모든 클래스의 모든 메서드에 적용
    @Pointcut("execution(* com.github.memoryh.nutritionfacts.api.controller..*.*(..))")
    private void controller(){}

    // com.github.memoryh.nutritionfacts.api.service 패키지 및 그 하위 패키지에 있는 모든 클래스의 모든 메서드에 적용
    @Pointcut("execution(* com.github.memoryh.nutritionfacts.api.service..*.*(..))")
    private void service(){}

    // com.github.memoryh.nutritionfacts.api.repository 패키지 및 그 하위 패키지에 있는 모든 클래스의 모든 메서드에 적용
    @Pointcut("execution(* com.github.memoryh.nutritionfacts.api.repository..*.*(..))")
    private void repository(){}

    // controller(), service(), repository() 포인트 컷 중 1개라도 만족하는 경우 Advice 실행
    @Around("controller() || service() || repository()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        TraceStatus status = null;

        try {
            // 예): ProductController.request(..)
            String message = joinPoint.getSignature().toShortString();

            // 로그 추적 시작
            status = logTrace.begin(message);

            // target 호출 (실제 호출할 클래스)
            Object result = joinPoint.proceed();

            // 로그 추적 종료
            logTrace.end(status);

            return result;

        } catch (Exception e) {
            // 로그 추적 중 예외 발생 시
            logTrace.exception(status, e);

            throw new RuntimeException(e);
        }

    }

}