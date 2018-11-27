import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
/**
 * Items represent all of the possible items in the game.
 * Specific items or a random item can be constructed.
 * Every item has a field for name, weight, whether it can
 * be picked up, and any bonus time assosciated with having
 * used it.
 * This information is stored in a HashMap of a String key and 
 * a List of Strings as its value.
 *
 * @author Harry Tennent
 * @version  25/11/18
 */
public class Items
{
    private Random randomGenerator;
    private String itemName;
    private int itemWeight;
    private boolean canBePickedUp;
    private int bonusTime;          //added time when item is used
    private HashMap<String, ArrayList<String>> itemHashMap;

    /**
     * Constructor for objects of class Items. Adds the item
     * data to the hash map.
     */
    public Items()
    {   
        randomGenerator = new Random();
        itemHashMap = new HashMap<String, ArrayList<String>>();

        ArrayList<String> oxygenTankData = new ArrayList<String>();
        oxygenTankData.add("40");   //weight
        oxygenTankData.add("true"); //can be picked up
        oxygenTankData.add("30");   //bonus time
        ArrayList<String> codesData = new ArrayList<String>();
        codesData.add("50");   //weight
        codesData.add("true"); //can be picked up
        codesData.add("0");   //bonus time
        ArrayList<String> bananaData = new ArrayList<String>();
        bananaData.add("10");   //weight
        bananaData.add("true"); //can be picked up
        bananaData.add("15");   //bonus time
        ArrayList<String> waterData = new ArrayList<String>();
        waterData.add("20");   //weight
        waterData.add("true"); //can be picked up
        waterData.add("15");   //bonus time
        ArrayList<String> chairData = new ArrayList<String>();
        chairData.add("20");   //weight
        chairData.add("false"); //can be picked up
        chairData.add("0");   //bonus time
        ArrayList<String> plantData = new ArrayList<String>();
        plantData.add("10");   //weight
        plantData.add("false"); //can be picked up
        plantData.add("0");   //bonus time
        ArrayList<String> tableData = new ArrayList<String>();
        tableData.add("30");   //weight
        tableData.add("false"); //can be picked up
        tableData.add("0");   //bonus time
        ArrayList<String> bookData = new ArrayList<String>();
        bookData.add("10");   //weight
        bookData.add("true"); //can be picked up
        bookData.add("0");   //bonus time
        ArrayList<String> lampData = new ArrayList<String>();
        lampData.add("20");   //weight
        lampData.add("false"); //can be picked up
        lampData.add("0");   //bonus time
        ArrayList<String> tabletData = new ArrayList<String>();
        tabletData.add("15");   //weight
        tabletData.add("false"); //can be picked up
        tabletData.add("0");   //bonus time
        ArrayList<String> cupboardData = new ArrayList<String>();
        cupboardData.add("40");   //weight
        cupboardData.add("false"); //can be picked up
        cupboardData.add("0");   //bonus time

        itemHashMap.put("oxygenTank", oxygenTankData);
        itemHashMap.put("codes", codesData);
        itemHashMap.put("banana", bananaData);
        itemHashMap.put("water", waterData);
        itemHashMap.put("chair", chairData);
        itemHashMap.put("plant", plantData);
        itemHashMap.put("table", tableData);
        itemHashMap.put("book", bookData);
        itemHashMap.put("lamp", lampData);
        itemHashMap.put("tablet", tabletData);
        itemHashMap.put("cupboard", cupboardData); 
    }
    /**
     * Randomly assign's an items properties to the
     * Items fields.
     */
    public void setRandomItemProperties() {
        Set<String> keySet = itemHashMap.keySet();
        ArrayList<String> keyList = new ArrayList<>();
        for (String i : keySet) {
            keyList.add(i);
        }
        int randIndex = randomGenerator.nextInt(keyList.size());
        itemName = keyList.get(randIndex);
        itemWeight = getWeight(itemName);
        canBePickedUp = getCanBePickedUp(itemName);
        bonusTime = getBonusTime(itemName);
    }
    /**
     * Calls setRandomItemProperties().
     */
    public void getRandomItem() {
        setRandomItemProperties();
    }
    /**
     * Sets the Items object's field to the data 
     * assosciated with the item name given.
     * @param itemName Name of the item.
     */
    public void getItem(String itemName) {
        this.itemName = itemName;
        itemWeight = getWeight(itemName);
        canBePickedUp = getCanBePickedUp(itemName);
        bonusTime = getBonusTime(itemName);
    }
    /**
     * Returns item name.
     * @return Name of the item if it is an item, null otherwise.
     */
    public String getName() {
       if (itemHashMap.containsKey(itemName)) {
           return itemName;
       } else {
           return null;
       }
    }
    /**
     * Returns the weight of the item.
     * @param Name of the item to get the weight for.
     * @return Item weight.
     */
    public int getWeight(String itemName) {
        if (itemHashMap.containsKey(itemName)) {
            String weight = itemHashMap.get(itemName).get(0); //Value 0 is item weight
            return Integer.parseInt(weight);
        } else {
            return 0;
        }
    }
    /**
     * Returns whether the item can be picked up or not.
     * @param Name of the item.
     * @return true if the item can be picked up, false otherwise.
     */
    public boolean getCanBePickedUp(String itemName) {
        if (itemHashMap.containsKey(itemName)) {
            return Boolean.parseBoolean(itemHashMap.get(itemName).get(1));//Value 1 is pick up boolean.
        } else {
            return false;
        }
    }
    /**
     * Returns the bonus time of the item, if any.
     * @param itemName Name of the item.
     * @return Bonus time, if any.
     */
    public int getBonusTime(String itemName) {
        if (itemHashMap.containsKey(itemName)) {
            return Integer.parseInt(itemHashMap.get(itemName).get(2));  //Value 2 is bonus time.
        } else {
            return 0;
        }
    }
    /**
     * A check to see if the specified item is an actual item.
     * @param itemName Name of the item.
     * @return true if the item is an item, false otherwise.
     */
    public boolean isAnItem(String itemName) {
        if (itemHashMap.containsKey(itemName)) {
            return true;
        } else {
            return false;
        }
    }
}
