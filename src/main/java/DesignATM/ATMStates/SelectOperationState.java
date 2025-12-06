package DesignATM.ATMStates;

import DesignATM.ATM;
import DesignATM.Card;
import DesignATM.TransactionType;

public class SelectOperationState extends ATMState{

    public SelectOperationState(){
        showOperations();
    }

    @Override
    public void selectOperation(ATM atmObject, Card card, TransactionType txnType) {

        switch (txnType){
            case CASH_WITHDRAWAL:
                atmObject.setCurrentATMStatus(new CashWithDrawalState());
                break;
            case BALANCE_CHECK:
                atmObject.setCurrentATMStatus(new CheckBalanceState());
                break;
            default: {
                System.out.println("Invalid Option");
                exit(atmObject);
            }
        }
    }

    @Override
    public void exit(ATM atmObject) {
        returnCard();
        atmObject.setCurrentATMStatus(new IdleState());
        System.out.println("Exit happened");
    }

    @Override
    public void returnCard(){
        System.out.println("Please collect your card");
    }

    private void showOperations(){
        System.out.println("Please Select the Operation");
        TransactionType.showALlTransactionTypes();
    }

}
