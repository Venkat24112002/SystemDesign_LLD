package DesignATM;


import DesignATM.ATMStates.ATMState;
import DesignATM.ATMStates.IdleState;

public class ATM {

    private static ATM atmObject = new ATM(); //Singleton: eager initialization

    private ATM() { } // 🔥 IMPORTANT: private constructor

    ATMState atmState;

    private int atmBalance;
    int noOfTwoThousandNotes;
    int noOfFiveHundredNotes;
    int noOfHundredNotes;


    public void setCurrentATMStatus(ATMState currentATMStatus){
        this.atmState = currentATMStatus;
    }

    public int getAtmBalance() {
        return atmBalance;
    }

    public void deductBankBalance(int withdrawAmount){
        atmBalance-=withdrawAmount;
    }


    public static ATM getATMObject(){
        atmObject.setCurrentATMStatus(new IdleState());
        return atmObject;
    }

    public void setATMBalance(int atmBalance, int noOfTwoThousandNotes,int noOfFiveHundredNotes, int noOfHundredNotes){
        this.atmBalance = atmBalance;
        this.noOfTwoThousandNotes = noOfTwoThousandNotes;
        this.noOfFiveHundredNotes = noOfFiveHundredNotes;
        this.noOfHundredNotes = noOfHundredNotes;
    }

    public int getNoOfTwoThousandNotes() {
        return noOfTwoThousandNotes;
    }

    public int getNoOfFiveHundredNotes() {
        return noOfFiveHundredNotes;
    }

    public int getNoOfOneHundredNotes() {
        return noOfHundredNotes;
    }

    public void deductTwoThousandNotes(int number) {
        noOfTwoThousandNotes = noOfTwoThousandNotes - number;
    }

    public void deductFiveHundredNotes(int number) {
        noOfFiveHundredNotes = noOfFiveHundredNotes - number;
    }

    public void deductOneHundredNotes(int number) {
        noOfHundredNotes = noOfHundredNotes - number;
    }

    public void printCurrentATMStatus(){
        System.out.println("Balance: " + atmBalance);
        System.out.println("2kNotes: " + noOfTwoThousandNotes);
        System.out.println("500Notes: " + noOfFiveHundredNotes);
        System.out.println("100Notes: " + noOfHundredNotes);

    }



}
