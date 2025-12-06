package DesignATM;

public class BankAccount {
    public int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void withdrawMoney(int amount) {
        balance -= amount;
    }

}
