package DesignATM.ATMStates;

import net.engineeringdigest.journalApp.CompositeDesignPattern.DesignATM.ATM;
import net.engineeringdigest.journalApp.CompositeDesignPattern.DesignATM.Card;

public class HasCardState extends ATMState{

    public HasCardState(){
        System.out.println("enter your card pin number");
    }

    @Override
    public void authenticatePin(ATM atm, Card card, int pin){
        boolean isCorrectPinEntered = card.isCorrectPin(pin);

        if(isCorrectPinEntered) {
            atm.setCurrentATMStatus(new SelectOperationState());
        } else {
            System.out.println("Invalid PIN number");
            exit(atm);
        }
    }

}
