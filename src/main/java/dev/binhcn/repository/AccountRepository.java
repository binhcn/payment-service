package dev.binhcn.repository;

import dev.binhcn.dto.Account;

public class AccountRepository {

    private final Account account;

    private static AccountRepository instance;

    private AccountRepository() {
        account = new Account();
    }

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    public static void init() {
        instance = new AccountRepository();
    }

    public Account getAccount() {
        return account;
    }
}
