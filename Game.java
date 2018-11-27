import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
/**
 *  This class is the main class of the "Zuul Station" application. 
 *  "Zuul Station" is a simple, text based adventure game.  Users 
 *  can walk around a space station.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * 
 * Edited and extended by Harry Tennent 25/11/18
 * 
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private long startTime;
    private Player player;
    private Timer timer;
    private boolean winCondition;
    private ArrayList<Room> roomsList;
    private HashMap<String, Character> charactersMap;
    private Character character;
    private Random randomGenerator;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        randomGenerator = new Random();
        roomsList = new ArrayList<>(createRooms());
        charactersMap = new HashMap<>();
        parser = new Parser();
        player = new Player();
        timer = new Timer();
        winCondition = false;
        createRandomCharacters(21);  //up to 20 characters randomly in game
    }
    /**
     * Create all the rooms and link their exits together.
     * @return An array list of all of the room objects.
     */
    private ArrayList<Room> createRooms()
    {
        Room livingQuarters, medicalBay, mainHall, 
        laboratory, reactor, escapePodRoom, transporterRoom,
        kitchen, storeRoom, gym;
        ArrayList<Room> rooms = new ArrayList<>();
        // create the rooms
        livingQuarters = new Room("in the living quarters");
        medicalBay = new Room("in the station's medical bay");
        mainHall = new Room("in the main hall");
        laboratory = new Room("in the laboratory");
        reactor = new Room("in the engine and reactor room");
        escapePodRoom = new Room("in the escape pod room");
        transporterRoom = new Room("in the transporter room");
        kitchen = new Room("in the kitchen");
        storeRoom = new Room("in the store room");
        gym = new Room("in the gym");
        // initialise room exits
        livingQuarters.setExit("west", medicalBay);
        livingQuarters.setExit("south", laboratory);
        livingQuarters.setExit("north", mainHall);
        medicalBay.setExit("east", livingQuarters);
        medicalBay.setExit("west", transporterRoom);
        medicalBay.setExit("north", kitchen);
        mainHall.setExit("south", livingQuarters);
        mainHall.setExit("west", kitchen);
        laboratory.setExit("north", livingQuarters);
        laboratory.setExit("west", reactor);
        laboratory.setExit("south", storeRoom);
        reactor.setExit("east", laboratory);
        reactor.setExit("west", escapePodRoom);
        reactor.setExit("south", gym);
        reactor.setExit("north", medicalBay);
        escapePodRoom.setExit("east", reactor);
        transporterRoom.setExit("east", medicalBay);
        kitchen.setExit("east", mainHall);
        kitchen.setExit("south", medicalBay);
        storeRoom.setExit("north", laboratory);
        storeRoom.setExit("west", gym);
        gym.setExit("north", reactor);
        gym.setExit("east", storeRoom);
        //add rooms to room list
        rooms.add(livingQuarters);
        rooms.add(medicalBay);
        rooms.add(mainHall);
        rooms.add(laboratory);
        rooms.add(reactor);
        rooms.add(escapePodRoom);
        rooms.add(transporterRoom);
        rooms.add(kitchen);
        rooms.add(storeRoom);
        rooms.add(gym);
        
        //stop more than 1 set of codes being created in the game
        ArrayList<Room> roomsWithCodes = new ArrayList<>();
        int roomWithCodesCounter = 0;
        for (int i = 0; i < rooms.size(); i++) {
            boolean hasCodes = rooms.get(i).hasCodes();
            if (hasCodes) {
                roomsWithCodes.add(rooms.get(i));
                roomWithCodesCounter++;
            }
            if (roomWithCodesCounter > 1) {
                rooms.get(i).removeItemFromRoom("codes");
                roomWithCodesCounter--;
            }
        }
        
        currentRoom = livingQuarters;  // start game in the livingQuarters
        return rooms;
    }
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        timer.startTimer();
        boolean finished = false;
        while ((!finished) && (timer.getTimeRemaining() > 0) && (!winCondition)) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        if (winCondition) {
            System.out.println("Congratulations! You found the codes and" +
            " reached the escape pods before time ran out.");
            timer.printTimeRemaining();
        } else if (timer.getTimeRemaining() <= 0) {
            System.out.println("You ran out of time and your " + 
            "oxygen supply is empty! Better luck next time.");
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new text-base adventure game.");
        System.out.println("This is set in a space station, " +  
        "hundreds of thousands of miles away from Earth.\nThe space " +
        "station has been in a crash and the oxygen supply to the "+
        "station has \nbeen cut off. To win, you must find the codes to release " +
        "the escape pods.\n" + "The existing oxygen supply runs out in 3 minutes.\n" + 
        "Starting now.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription(""));
    }
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            currentRoom = goRoom(command);
            printCharactersInCurrentRoom();
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("pickup")) {
            pickupItem(command);
        } else if (commandWord.equals("use")) {
            if (testWinCondition(command)) {
               return testWinCondition(command);
            } else {
                timer.addBonusTime(useItem(command)); //useItem returns extra time from using item
            }
        } else if (commandWord.equals("inventory")) {
            showInventory();
        } else if (commandWord.equals("drop")) {
            dropItem(command);
        } else if (commandWord.equals("back")) {
            back();
        } else if (commandWord.equals("timer")) {
            timer.printTimeRemaining();
        } else if (commandWord.equals("give")) {
            giveItem(command);
        }
        // else command not recognised.
        return wantToQuit;
    }
    /**
     * Given a max. number of characters, create a random number
     * of characters and put them in the game.
     * @param numberOfCharacters Maximum number of characters
     */
    private void createRandomCharacters(int numberOfCharacters) {
        //random number + 1 so there is always at least 1 character in the game
        int randNumber = randomGenerator.nextInt(numberOfCharacters) + 1;
        int i = 0;
        while (i < randNumber) {
            character = new Character();
            charactersMap.put(character.getName(), character);
            //add character to a random room
            int randIndex = randomGenerator.nextInt(roomsList.size());
            character.setCurrentRoom(roomsList.get(randIndex));
            i++;
        }
    }
     /**
      * Creates and prints a string containin the names of all of the
      * characters in the same room as the player.
      */
    private void printCharactersInCurrentRoom() {
        String charactersInRoom = "";
        for (Character character : charactersMap.values()) {
            if (character.getCurrentRoom().equals(currentRoom)) { 
                //player is in the same room as the character
                charactersInRoom += character.getName() + " ";
            }   //other print out an empty string
        }
        System.out.println(currentRoom.getLongDescription(charactersInRoom));
    }
    //implementation of user commands
    /**
     * Print out some help information.
     * Here we print some details of the aim of the game and  a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You have to try and find the escape pod codes.");
        System.out.println("Then, you must take them to the escape pod room");
        System.out.println("and use them before the oxygen supply runs out.");
        System.out.println("Good luck!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    /**
     * Checks whether the player has the codes in their backpack, and checks
     * that they are in the escape pod room. If both of these conditions are true,
     * then the win condition is set to true, otherwise, it is false.
     * @param command The command to be processed.
     * @return true If the player is in the escape pod room with the codes, false otherwise.
     */
   private boolean testWinCondition(Command command) {   
        String item = command.getSecondWord();
        boolean inEscapeRoom = currentRoom.getShortDescription().equals("in the escape pod room");
        boolean hasCodes = false;
        if (player.getBackpackItem(item) != null) {
             hasCodes = player.getBackpackItem(item).equals("codes");
        }
        if (player.getBackpackItem(item) != null && hasCodes && inEscapeRoom) {
            winCondition = true;
            return winCondition;
        }
        return false;    //only get to this point if the condition was not met
   }
   /** 
     * Try to go in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message. If the player enters, the
     * transporter room, randomly pick a room to move them to.
     * @param command Command stating which direction to go in.
     * @return A room object of the room the player is currently in.
     */
    private Room goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return null;
        }
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else if (nextRoom.getShortDescription().equals("in the transporter room")) {
            System.out.println("You entered the broken transporter room!");
            int randInt = randomGenerator.nextInt(roomsList.size());
            Room randomRoom = roomsList.get(randInt);
            randomRoom.setPreviousRoom(currentRoom);
            currentRoom = randomRoom;
        }
        else {
            nextRoom.setPreviousRoom(currentRoom);
            currentRoom = nextRoom;     //player moves to another room
        }
        return currentRoom;
    }
    /**
     * This method is called every time the player calls the 'go' method,
     * and it moves the characters in the game in a random direction.
     * @param character Character object for the character to be moved. 
     */
    private void moveCharacter(Character character) {
        ArrayList<String> exitList = character.getCurrentRoom().getExits();
        int randIndex = randomGenerator.nextInt(exitList.size()); //north,east,south,west
        String randomDirection = exitList.get(randIndex);
        Room nextRoom = character.getCurrentRoom().getExit(randomDirection);
        if (nextRoom != null) {         
            character.setCurrentRoom(nextRoom);
        }
    }
    /**
     * A method to pick an item up from the room the player is in
     * and add it to their backpack, provided that they have enough
     * space and the item can be picked up.
     * @param command Command containing the item to pick up.
     */
    private void pickupItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Pick up what?");
            return;
        }
        String item = command.getSecondWord();
        Items roomItem = new Items();
        if (roomItem.isAnItem(item)) {
            int itemWeight = roomItem.getWeight(item);
            if (item.equals(null)) {
                System.out.println("There is no " + item + " to pick up.");
            } else if (roomItem.getCanBePickedUp(item)) {
                player.addBackpackItem(item, itemWeight);
                currentRoom.removeItemFromRoom(item);
            } else {
                System.out.println("You can't pick this item up.");
            }
        } else {
            System.out.println(item + " is not an item.");
        }
        
    }
    /**
     * If the specified item has bonus time, add this to the game clock and
     * remove it from the player's backpack.
     * @param command Command containing the item to be used.
     * @return An int of the bonus time. 
     */
    private int useItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Use what?");
            return 0;
        }
        String item = command.getSecondWord();
        Items roomItem = new Items();
        if (player.getBackpackItem(item) != null) {
            int bonusTime = roomItem.getBonusTime(item);
            int itemWeight = roomItem.getWeight(item);
            if (bonusTime == 0) {
                System.out.println("There is no bonus time for using this item.");
                return 0;
            }
            player.removeBackpackItem(item, itemWeight);
            return bonusTime;
        } else {
            System.out.println("You don't have that item in your backpack.");
            return 0;
        }
    }
    /**
     * Print out the contents of the player's backpack.
     */
    private void showInventory() {
        player.printBackpackItems();
    }
    /**
     * Remove item from the player's backpack and return it to the room.
     * @param command Command containing the item to drop.
     */
    private void dropItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        String item = command.getSecondWord();
        Items roomItem = new Items();
        if (player.getBackpackItem(item) != null) {
            int itemWeight = roomItem.getWeight(item);
            player.removeBackpackItem(item, itemWeight);
            System.out.println(item + " removed from your backpack.");
            currentRoom.returnItemToRoom(item);
        } else {
            System.out.println("You don't have that item in your backpack.");
            return;
        }
    }
    /**
     * Take the player back to the room they were previously in.
     */
    private void back() {
        // Try to leave current room.
        Room nextRoom = currentRoom.getPreviousRoom();
        if (nextRoom == null) {
            System.out.println("You haven't been anywhere yet!");
        }
        else {
            currentRoom.setPreviousRoom(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription(""));
        }
    }
    /**
     * A three-word command that allows the player to give items in their
     * backpack to other characters in the game, provided they are in the same
     * room.
     * @param command Command contain the item to give and the person to give it to.
     */
    private void giveItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to give...
            System.out.println("Give what?");
            return;
        } else if (!command.hasThirdWord()) {
            System.out.println("Give to who?");
            return;
        }
        
        String item = command.getSecondWord();
        String name = command.getThirdWord();
        
        Character character = charactersMap.get(name);
        Items roomItem = new Items();
        int itemWeight;
        //checks player has that item and is in the same room as the character
        if ((player.getBackpackItem(item) != null) && 
        (character.getCurrentRoomString().equals(currentRoom.getShortDescription()))) {
            character.addBackpackItem(item);
            itemWeight = roomItem.getWeight(item);
            player.removeBackpackItem(item, itemWeight);
            System.out.println(item + " given to " + character.getName());
        } else if (!character.getCurrentRoomString().equals(currentRoom.getShortDescription())) {
            System.out.println(character.getName() + " is not in this room.");
        } else if (player.getBackpackItem(item) == null){
            System.out.println("You do not have this item in your backpack.");
        } else {
            System.out.println("You cannot give this item.");
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}  
