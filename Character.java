import java.util.ArrayList;
import java.util.Random;
/**
 * Character class represents other characters in the game.
 * They can walk throughout the game. The player can give them items
 * if they wish to.
 *
 * @author Harry Tennent
 * @version 25/11/18
 */
public class Character
{
    // instance variables
    ArrayList<String> names;
    Random randomGenerator;
    String name;
    Room currentRoom;
    ArrayList<String> backpack;
    
    /**
     * Constructor for objects of class Character
     */
    public Character()
    {
        // initialise instance variables
        randomGenerator = new Random();
        names = new ArrayList<>();
        names.add("CommanderBob");
        names.add("PrivateDylan");
        names.add("PrivateRyan");
        names.add("LieutenantFiona");
        names.add("CaptainJane");
        currentRoom = null;
        setName();
        backpack = new ArrayList<>();
    }
    /**
     * Return name of the character.
     * @return Character name.
     */
    public String getName()
    {
        return name;
    }
    /**
     * Random pick a name for the character.
     */
    private void setName() {
        int randIndex = randomGenerator.nextInt(names.size());
        name = names.get(randIndex);
    }
    /**
     * Set the current room the character is in.
     * @param room Room object the character is in.
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }
    /**
     * Return the room the character is in.
     * @return Current room object.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     * Returns a string of the current room's description.
     * @return Current room's description.
     */
    public String getCurrentRoomString() {
        return currentRoom.getShortDescription();
    }
    /**
     * Add an item to the backpack.
     * @param itemName Name of item to add.
     */
    public void addBackpackItem(String itemName) {
        backpack.add(itemName);
    }

}
