package com.talspektor.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class MyCloudLogAspect {

    @Before("LuvAopExpressions.forDaoPackageNoGetterSetter()")
    public void logToCouldAsync() {
        System.out.println("\n=====>>> Perform Logging to Cloud in async fashion");
    }
}
