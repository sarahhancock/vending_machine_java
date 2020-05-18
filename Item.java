/*
 * This class represents the vending machine itself.
 * It has a cashVal, which is the amount of money that has been inserted.
 * It also has a variable itemSelected, which keeps track of the currently selected item.
 * It also has a finite amount of change that it can give, which I have 
 * arbitrarily set as 100 of each type of coin.
 * */

import java.util.HashMap;
import java.util.Scanner;

public class VendingMachine {
    
    private int cashVal; //value of cash inserted, in pennies
    private Item itemSelected; 
    private int qChange = 100;
    private int dChange = 100;
    private int nChange = 100;
    private Coin quarter = new Coin("quarter");
    private Coin nickel = new Coin("nickel");
    private Coin dime = new Coin("dime");
    
 
    /*
     * Creates an instance of a vending machine. 
     * There is no cash inserted yet, and no item selected.
     * */
    public VendingMachine(){
        cashVal = 0;
        itemSelected = null;
    }
    
    /*
     * Inserts a coin, and updates the value the user has put in the machine.
     * */
    public void insertCash(Coin c) {
        cashVal += c.getValue();
        
   }
    
    /*
     * Selects an item, and then dispenses it if there is enough money in the machine.
     * Otherwise, it still selects an item, but also informs the user that they need to 
     * insert more coins.
     * */
    public void selectItem(Item i) {
        int price = i.getPrice();
        itemSelected = i;
        System.out.println("Item selected.");
        if (price <= cashVal) {
            dispenseItem();
            cashVal -= price;
        } else {
            System.out.println("You need to insert more coins for your item to be dispensed.");
        }
    }
    
    /*
     * Dispenses an item and informs the user that the item has been dispensed.
     * */
    public Item dispenseItem(){
        System.out.println("Item dispensed.");
        return itemSelected;        
    }
    
    /*
     * Calculates the amount of change needed based on the amount of
     * money in the machine. Returns the frequency of each coin to be 
     * returned in a HashMap.
     * */
    public HashMap<Coin, Integer> dispenseChange(){
        HashMap<Coin, Integer> coins = new HashMap<Coin, Integer>();
  
        coins.put(quarter, (cashVal / 25));
        cashVal = cashVal % 25;

        coins.put(dime, (cashVal / 10));
        cashVal = cashVal % 10;

        coins.put(nickel, (cashVal / 5));
        cashVal = cashVal % 5;
        
        return coins;
    }
 
    /*
     * Prompts the user to either insert coins or select an item until they do not
     * want to perform any more actions. Returns the user's change at the end
     * of the interaction.
     * */
    public void vend(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the vending machine!");
        String keepVending = "yes";
        while (keepVending.compareTo("yes") == 0) {
            System.out.println("Type 'select' to select an item," + 
                               " or type 'insert' to insert a coin.");
            String action = (input.next()).toLowerCase();
            
            if (action.compareTo("select") == 0) {
                System.out.println("Please type an integer from 1 to 9 to select your item.");
                int itemIndex = input.nextInt();
                if (itemIndex <= 9 && itemIndex >= 1) {
                    Item i = new Item(itemIndex);
                    selectItem(i);    
                } else {
                    System.out.println("Sorry, we don't have that item.");
                }
                
            } else if (action.compareTo("insert") == 0) {
                System.out.println("Please type the name of the coin that you are inserting.");
                String coinName = (input.next()).toLowerCase();
                if (coinName.compareTo("quarter") == 0 || coinName.compareTo("nickel") == 0 ||
                    coinName.compareTo("dime") == 0) {
                    Coin c = new Coin(coinName);
                    insertCash(c);
                    System.out.println("Coin inserted.");
                    double cashValDollars = (double) cashVal / 100.;
                    System.out.printf("You have inserted a total of $%.2f. ", cashValDollars);
                } else {
                    System.out.println("Sorry, this machine does not accept that kind of coin.");
                }
                
                
            } else {
                System.out.println("Sorry, " + action + " is not an option.");
            }
            
            System.out.println("If you would like to select an item or insert a" + 
                               " coin again, type 'yes'. Otherwise, type 'no'.");
            keepVending = (input.next()).toLowerCase();
        }
        
        if (cashVal > 0) {
            HashMap<Coin, Integer> change = dispenseChange();
            
            int q = change.get(quarter);
            int d = change.get(dime);
            int n = change.get(nickel);
            
            if (q > qChange || d > dChange || n > nChange) {
                System.out.println("Sorry, this machine is out of change." + 
                                   " Please contact a supervisor.");
            } else {
                qChange -= q;
                dChange -= d;
                nChange -= n;
                System.out.println("Your change is " + q + " quarter(s), " + d + 
                                   " dime(s), and " + n + " nickel(s).") ;
            }
        }
        
        System.out.println("Please come again soon!");

    }
}