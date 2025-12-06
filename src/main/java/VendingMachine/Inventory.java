package VendingMachine;


public class Inventory {
    ItemSelf[] inventory = null;

    public Inventory(int itemCount){
        inventory = new ItemSelf[itemCount];
        int startCode = 101;
        for(int i=0;i<itemCount;i++){
            ItemSelf itemSelf = new ItemSelf();
            itemSelf.setCode(startCode+i);
            inventory[i] = itemSelf;
            inventory[i].setSoldOut(true);
        }
    }

    public void addItem(Item item, int codeNumber) throws Exception {

        for(ItemSelf itemSelf : inventory){
            if(itemSelf.code == codeNumber){
                if(itemSelf.isSoldOut()) {
                    itemSelf.item = item;
                    itemSelf.setSoldOut(false);
                } else {
                    throw new Exception("already item is present");
                }
            }
        }
    }

    public Item getItem(int codeNumber) throws Exception {

        for(ItemSelf itemSelf : inventory){
            if(itemSelf.code == codeNumber){
                if(itemSelf.isSoldOut()){
                    throw new Exception("item alread sold out");
                }
                else return itemSelf.item;
            }
        }
        throw new Exception("Invalid code");
    }

    public void updateSoldOutItem(int codeNumber){
        for (ItemSelf itemSelf : inventory) {
            if (itemSelf.code == codeNumber) {
                itemSelf.setSoldOut(true);
            }
        }
    }


}
