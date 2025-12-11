package AdaptorDesignPattern.Adapter;

import AdaptorDesignPattern.Adaptee.WeightMachine;

public class WeightMachineAdapterImpl implements WeightMachineAdapter{

    public WeightMachine weightMachine;

    public WeightMachineAdapterImpl(WeightMachine weightMachine){
        this.weightMachine = weightMachine;
    }

    @Override
    public double getWeightInKg() {
        return weightMachine.getWeightInPound()*0.45;
    }
}
