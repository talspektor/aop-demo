package com.talspektor.aopdemo.aspect;

import static java.util.Arrays.stream;

import com.talspektor.aopdemo.Account;
import java.util.List;
import java.util.stream.Collectors;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    @Around("execution(* com.talspektor.aopdemo.service.*.getFortune(..))")
    public Object afterGetFortuneThrows(ProceedingJoinPoint proceedingJoinPoint)
        throws Throwable {
        Object result;
        try {
            result = proceedingJoinPoint.proceed();
            return result;
        } catch (Throwable e) {
            System.out.println(e.getMessage());

            throw e;
        }
    }

    // Use with caution
//    @Around("execution(* com.talspektor.aopdemo.service.*.getFortune(..))")
    public Object afterGetFortune(ProceedingJoinPoint proceedingJoinPoint) {

        Object result;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
            System.out.println("@Around advice: We have a problem " + e);
            result = "Major accident! But no worries, your private AOP helicopter is on the way!";
        }

        return result;
    }

    @Around("execution(* com.talspektor.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String method = proceedingJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @After (finally) on method : " + method);

        long begin = System.currentTimeMillis();

        Object result = proceedingJoinPoint.proceed();

        long end = System.currentTimeMillis();

        System.out.println("\n=====>>> Duration: " + (end - begin) / 1000.0 + " seconds");

        return result;
    }

    @After("execution(* com.talspektor.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @After (finally) on method : " + method);

    }

    @AfterThrowing(
        pointcut = "execution(* com.talspektor.aopdemo.dao.AccountDAO.findAccounts(..))",
        throwing = "theExc")
    public void afterThrowingFindAccountAdvice(JoinPoint joinPoint, Exception theExc) {
        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterThrowing on method : " + method);

        System.out.println("\n=====>>> The exception is : " + theExc);

    }

    @AfterReturning(
        pointcut = "execution(* com.talspektor.aopdemo.dao.AccountDAO.findAccounts(..))",
        returning = "result")
    public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, List<Account> result) {

        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterReturning on method : " + method);

        System.out.println("\n=====>>> result is : " + result);

        convertAccountNamesToUpperCase(result);

        System.out.println("\n=====>>> result is : " + result);

    }

    private void convertAccountNamesToUpperCase(List<Account> result) {

        result.stream()
            .peek(account -> {
                account.setName(account.getName().toUpperCase());
                account.setLevel(account.getLevel().toUpperCase());
            })
            .collect(Collectors.toList());
    }

    @Before("LuvAopExpressions.forDaoPackage()")
    public void beforeAddAccountAdvice(JoinPoint joinPoint) {
        System.out.println("\n=====>>> Executing @Before advice on method()");

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Method: " + methodSignature);

        Object[] args = joinPoint.getArgs();
        stream(args).forEach(arg -> {
            System.out.println(arg);
            if (arg instanceof Account account) {
                System.out.println("Account name: " + account.getName());
                System.out.println("Account level: " + account.getLevel());
            }
        });
    }

//    @Before("forDaoPackageNoGetterSetter()")
//    public void beforeAddAccountAdviceNoGetterSetter() {
//        System.out.println("\n=====>>> Executing @Before advice on method()");
//    }
}
