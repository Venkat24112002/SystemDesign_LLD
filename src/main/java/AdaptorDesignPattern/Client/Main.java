package AdaptorDesignPattern.Client;

import AdaptorDesignPattern.Adaptee.WeightMachineForBabies;
import AdaptorDesignPattern.Adapter.WeightMachineAdapter;
import AdaptorDesignPattern.Adapter.WeightMachineAdapterImpl;

public class Main {

    public static void main(String []args){
        WeightMachineAdapter weightMachineAdapter = new WeightMachineAdapterImpl(new WeightMachineForBabies());
        System.out.println(weightMachineAdapter.getWeightInKg());
    }
}
