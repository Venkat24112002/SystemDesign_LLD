package DesignATM;

public class Card {
    int CVV;
    int pinNumber=112233;
    int cardNumber;
    String holderName;
    int expiryDate;
    BankAccount userBankAccount;

    public boolean isCorrectPin(int pin) {
        return pin == pinNumber;
    }

    public void withdrawMoney(int amount){
        userBankAccount.withdrawMoney(amount);
    }

    public int getBankBalance(){
        return userBankAccount.balance;
    }

    public void deductBankBalance(int money){
        userBankAccount.withdrawMoney(money);
    }


}
