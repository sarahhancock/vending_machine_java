/*
 * This class represents a item.
 * Each has an index, and we assume there is a picture on the machine which shows
 * item that the index corresponds to.
 * Each item also has a price, which I have arbitrarily set as the index*25, but
 * an actual vending machine could have real price values set here.
 * */
public class Item {
    
    private int index;
    private int price;

    
    public Item(int i) {
        index = i;
        price = i*25;
    }
    
    public int getPrice(){
        return price;
    }
    
    
}