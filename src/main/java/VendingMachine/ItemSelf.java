package VendingMachine;

public class ItemSelf {

    int code;
    boolean isSoldOut;
    Item item;


    public boolean isSoldOut() {
        return isSoldOut;
    }

    public int getCode() {
        return code;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }
}
