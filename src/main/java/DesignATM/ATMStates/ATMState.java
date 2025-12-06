package DesignATM.ATMStates;

import net.engineeringdigest.journalApp.CompositeDesignPattern.DesignATM.ATM;
import net.engineeringdigest.journalApp.CompositeDesignPattern.DesignATM.Card;
import net.engineeringdigest.journalApp.CompositeDesignPattern.DesignATM.TransactionType;

public abstract class ATMState {

    public void insertCard(ATM atm, Card card) {
        System.out.println("OOPS!! Something went wrong");
    }

    public void authenticatePin(ATM atm, Card card, int pin){
        System.out.println("OOPS!! Something went wrong");
    }

    public void selectOperation(ATM atm, Card card, TransactionType txnType){
        System.out.println("OOPS!! Something went wrong");
    }

    public void cashWithDrawal(ATM atm, Card card , int wiithdrawAmount) {
        System.out.println("OOPS!! Something went wrong");
    }

    public void displayBalanace(ATM atm, Card card) {
        System.out.println("OOPS!! Something went wrong");
    }

    public void returnCard() {
        System.out.println("OOPS!! Something went wrong");
    }

    public void exit(ATM atm) {
        System.out.println("OOPS!! Something went wrong");
    }
}
