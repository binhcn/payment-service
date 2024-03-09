package dev.binhcn.dto;

public class Account {

    private long balance;

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void addBalance(long balance) {
        this.balance += balance;
    }
    public long getBalance() {
        return balance;
    }
}
