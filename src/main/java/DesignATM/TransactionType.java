package DesignATM;

public enum TransactionType {
    CASH_WITHDRAWAL,
    BALANCE_CHECK;

    public static void showALlTransactionTypes() {
        for(TransactionType type : TransactionType.values()) {
            System.out.println(type.name());
        }
    }
}
