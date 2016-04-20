package com.sx.aop.internal;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;

@Aspect
public class Aop {
    private static volatile boolean enabled = true;

    @Pointcut("within(@com.sx.aop.annotation.Aop *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(@com.sx.aop.annotation.Aop * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }

    @Around("method()")
    public Object inject(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!enabled) return joinPoint.proceed();

        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        Class<?> cls = codeSignature.getDeclaringType();
        String methodName = codeSignature.getName();
        Object[] parameterValues = joinPoint.getArgs();

        AopManager.Inject inject = AopManager.getInject(cls);

        Object result;

        if (inject == null || !inject.before(methodName)) {
            result = joinPoint.proceed();
        } else {
            result = inject.process(methodName, parameterValues);
        }

        if (inject == null) {
            return result;
        } else {
            return inject.after(methodName, result);
        }
    }

}
