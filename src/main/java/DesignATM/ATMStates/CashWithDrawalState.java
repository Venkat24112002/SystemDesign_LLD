package DesignATM.ATMStates;

import net.engineeringdigest.journalApp.CompositeDesignPattern.DesignATM.ATM;
import net.engineeringdigest.journalApp.CompositeDesignPattern.DesignATM.Card;

public class CashWithDrawalState extends ATMState{

    public CashWithDrawalState() {
        System.out.println("Please enter the Withdrawal Amount");
    }

    @Override
    public void cashWithDrawal(ATM atmObject, Card card, int withdrawalAmountRequest) {
        if (atmObject.getAtmBalance() < withdrawalAmountRequest) {
            System.out.println("Insufficient fund in the ATM Machine");
            exit(atmObject);
        } else if (card.getBankBalance() < withdrawalAmountRequest) {
            System.out.println("Insufficient fund in the your Bank Account");
            exit(atmObject);
        } else {
            card.deductBankBalance(withdrawalAmountRequest);
            atmObject.deductBankBalance(withdrawalAmountRequest);


            exit(atmObject);
        }

    }
    @Override
    public void exit(ATM atmObject) {
        returnCard();
        atmObject.setCurrentATMStatus(new IdleState());
        System.out.println("Exit happens");
    }

    @Override
    public void returnCard() {
        System.out.println("Please collect your card");
    }


}
