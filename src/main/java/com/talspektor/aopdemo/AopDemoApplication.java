package com.talspektor.aopdemo;

import com.talspektor.aopdemo.dao.AccountDAO;
import com.talspektor.aopdemo.dao.MembershipDAO;
import com.talspektor.aopdemo.service.TrafficFortuneService;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AopDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AccountDAO accountDAO,
        MembershipDAO membershipDAO,
        TrafficFortuneService trafficFortuneService
    ) {
        return runner -> demoTheAroundAdviceRethrowException(trafficFortuneService);
    }

    private void demoTheAroundAdviceRethrowException(TrafficFortuneService trafficFortuneService) {

        System.out.println("\nMain Program : demoTheAroundAdviceRethrowException");

        System.out.println("Calling getFortune");

        boolean tripWire = true;
        String data = trafficFortuneService.getFortune(tripWire);

        System.out.println("\nMy fortune is: " + data);

        System.out.println("Finished");
    }

    private void demoTheAroundAdviceHandleException(TrafficFortuneService trafficFortuneService) {

        System.out.println("\nMain Program : demoTheAroundAdviceHandleException");

        System.out.println("Calling getFortune");


        boolean tripWire = true;
        String data = trafficFortuneService.getFortune(tripWire);

        System.out.println("\nMy fortune is: " + data);

        System.out.println("Finished");
    }

    private void demoTheAroundAdvice(TrafficFortuneService trafficFortuneService) {

        System.out.println("\nMain Program : demoTheAroundAdvice");

        System.out.println("Calling getFortune");

        String data = trafficFortuneService.getFortune();

        System.out.println("\nMy fortune is: " + data);

        System.out.println("Finished");
    }

    private void demoTheAfterAdvice(AccountDAO accountDAO) {
        List<Account> accountList = null;

        try {
            boolean tripWire = false;
            accountList = accountDAO.findAccounts(tripWire);
        } catch (Exception e) {
            System.out.println("\n\nMain Program ... caught exception: " + e);
        }

        System.out.println("\n\nMain Program : demoTheAfterThrowingAdvice");
        System.out.println("----");

        System.out.println(accountList);

        System.out.println("\n");
    }

    private void demoTheAfterThrowingAdvice(AccountDAO accountDAO) {

        List<Account> accountList = null;

        try {
            boolean tripWire = true;

            accountList = accountDAO.findAccounts(tripWire);
        } catch (Exception e) {
            System.out.println("\n\nMain Program ... caught exception: " + e);
        }

        System.out.println("\n\nMain Program : demoTheAfterThrowingAdvice");
        System.out.println("----");

        System.out.println(accountList);

        System.out.println("\n");
    }

    private void demoTheAfterReturningAdvice(AccountDAO accountDAO) {

        List<Account> accountList = accountDAO.findAccounts();

        System.out.println("\n\nMain Program : demoTheAfterReturningAdvice");
        System.out.println("----");

        System.out.println(accountList);

        System.out.println("\n");
    }

    private void demoTheBeforeAdvice(AccountDAO accountDAO, MembershipDAO membershipDAO) {

        Account account = new Account();
        account.setName("Madhu");
        account.setLevel("Platinum");
        accountDAO.addAccount(account, true);

        accountDAO.doWork();

        accountDAO.setName("foobar");
        accountDAO.setServiceCode("silver");
        accountDAO.getName();
        accountDAO.getServiceCode();

        membershipDAO.addSillyMember();
        membershipDAO.goToSleep();
    }

}
