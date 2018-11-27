import java.util.Set;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Zuul Station" application. 
 * "Zuul Station" is a simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room. There are also items in
 * room which can be picked up and dropped, adding and removing them 
 * from the room respectively.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * 
 * Extended and edited by Harry Tennent 25/11/18.
 */

public class Room 
{
    private String description;
    private Room previousRoom;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Items> itemsInRoom; 
    private Random randomGenerator;
    private Items roomItem;
    private boolean hasCodes;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        previousRoom = null;
        hasCodes = false;
        exits = new HashMap<>();
        randomGenerator = new Random();
        itemsInRoom = new HashMap<>();
        createRoomItems(6);
    }
    /**
     * Given a max number of items for the room, create a random number
     * of items and add them to the room.
     * @param itemNumber Max number of items.
     */
    private void createRoomItems(int itemNumber) {
        int randItemNumber = randomGenerator.nextInt(itemNumber); //up to 5 items in room
        int i = 0;
        while (i <= randItemNumber) {
            roomItem = new Items();
            roomItem.getRandomItem();
            if (roomItem.getName().equals("codes")) {
                hasCodes = true;
            }
            itemsInRoom.put(roomItem.getName(), roomItem);
            i++;
        }
    }
    /**
     * Remove item from room.
     * @param item Item to remove.
     */
    public void removeItemFromRoom(String item) {
        itemsInRoom.remove(item);
    }
    /**
     * Put item back into the room.
     * @param itemName Name of item to add back.
     */
    public void returnItemToRoom(String itemName) {
        roomItem = new Items();
        roomItem.getItem(itemName);
        itemsInRoom.put(roomItem.getName(), roomItem);
    }
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items in the room: banana
     *     Characters in the room: PrivateRyan
     * @return A long description of this room
     */
    public String getLongDescription(String charactersInRoom)
    {
        String roomItemsString = "\nItems in the room: ";
        String characterString = "\nCharacters in the room: ";
        String longDescription = "You are " + description +
            ".\n" + getExitString();
        for (String item : itemsInRoom.keySet()) {
            roomItemsString += (item + "   "); 
        }
        characterString += charactersInRoom;
        longDescription += roomItemsString + characterString;
        return longDescription;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    /**
     * Return an array list of all the exits in the room.
     * @return Array list of the room's exits.
     */
    public ArrayList<String> getExits() {
        ArrayList<String> exitList = new ArrayList<>();
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            exitList.add(exit);
        }
        return exitList;
    }
    /**
     * Store the previous room the player was in.
     * @param room Previous room object.
     */
    public void setPreviousRoom(Room room) {
        previousRoom = room;
    }
    /**
     * Return the previous room the player was in.
     * @return Previous room object.
     */
    public Room getPreviousRoom() {
        return previousRoom;
    }
    
    public boolean hasCodes() {
        return hasCodes;
    }
}