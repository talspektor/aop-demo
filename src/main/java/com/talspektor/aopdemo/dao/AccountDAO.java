package com.talspektor.aopdemo.dao;

import com.talspektor.aopdemo.Account;
import java.util.List;

public interface AccountDAO {

    void addAccount(Account account, boolean vipFlag);

    boolean doWork();

    void setName(String name);
    String getName();
    void setServiceCode(String serviceCode);
    String getServiceCode();

    List<Account> findAccounts();

    List<Account> findAccounts(boolean tripWire);
}
