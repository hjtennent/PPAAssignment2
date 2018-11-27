import java.util.ArrayList;
/**
 * Represents the player in the game. Keeps track of the items
 * in their backpack, total weight of the backpack and provides
 * methods to add, remove and print items of the backpack.
 *
 * @author Harry Tennent
 * @version 15/11/18
 */
public class Player
{
    // instance variables
    private static final int MAX_WEIGHT = 100;
    private ArrayList<String> backpack;
    private int currentBackpackWeight;
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
        backpack = new ArrayList<>();
        currentBackpackWeight = 0;
    }
    /**
     * Returns the backpack item at the specified index.
     * @param index Index of item to return.
     * @return String Backpack item.
     */
    public String getBackpackItem(int index) {
        return backpack.get(index);
    }
    /**
     * Returns the backpack item matching the given item name.
     * @param itemName Name of the item to return.
     * @return String Backpck item if found or null otherwise.
     */
    public String getBackpackItem(String itemName) {
        if (backpack.contains(itemName)) {
            return backpack.get(backpack.indexOf(itemName));
        } else {
            return null;
        }
    }
    /**
     * Add an item to the player's backpack.
     * @param itemName Name of the item to add.
     * @param itemWeight Weight of the item to add. 
     */
    public void addBackpackItem(String itemName, int itemWeight) {
        int availableSpace = MAX_WEIGHT - currentBackpackWeight;
        if (availableSpace >= itemWeight) {
            backpack.add(itemName);
            currentBackpackWeight += itemWeight;
            System.out.println(itemName + " added to your backpack!");
        } else {
            System.out.println("You don't have enough space!");
        }
        
    }
    /**
     * Remove an item from the player's backpack.
     * @param itemName Name of the item to add.
     * @param itemWeight Weight of the item to add.
     */
    public void removeBackpackItem(String itemName, int itemWeight) {
        currentBackpackWeight -= itemWeight;
        if (backpack.contains(itemName)) {
            backpack.remove(itemName);
        } else {
            System.out.println("That item is not in your backpack.");
        }
    }
    /**
     * Returns the space left in the backpack.
     * @return int of available space.
     */
    public int spaceAvailable() {
        return MAX_WEIGHT - currentBackpackWeight;
    }
    /**
     * Print a string of all the items in the backpack to the console.
     */
    public void printBackpackItems() {
        String inventoryString = "Items in backpack: ";
        for (String i : backpack) {
            inventoryString += (i + " ");
        }
        System.out.println(inventoryString);
    }
}
