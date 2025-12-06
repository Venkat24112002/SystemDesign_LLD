package DesignATM.ATMStates;

import net.engineeringdigest.journalApp.CompositeDesignPattern.DesignATM.ATM;
import net.engineeringdigest.journalApp.CompositeDesignPattern.DesignATM.Card;

public class IdleState extends ATMState{

    @Override
    public void insertCard(ATM atm, Card card) {
        System.out.println("Card is inserted");
        atm.setCurrentATMStatus(new HasCardState());
    }
}
