
/**
 * The timer class manages the game timer. It it started as soon
 * as the game starts. Bonus time can be added to the timer by
 * using items. When the timer runs out, the game ends.
 *
 * @author Harry Tennent
 * @version 25/11/18
 */
public class Timer
{
    private long startTime;
    private long bonusTime;
    private long timeInSeconds;
    /**
     * Constructor for objects of class Timer
     */
    public Timer()
    {
        // initialise instance variables
        startTime = 0;
        bonusTime = 0;
    }
    
    /**
     * Timer code taken from this StackOverflow answer: 
     * https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java/14323134
     * From user "AgilePro" on the 15/11/18.
     * 
     * Starts the timer.
     */
    public void startTimer() {
        startTime = System.currentTimeMillis();
    }
    /**
     * Timer code taken and edited from this StackOverflow answer: 
     * https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java/14323134
     * From user "AgilePro" on the 15/11/18.
     * 
     * Returns the time remaining on the clock.
     * @return long value of the time remaining in seconds.
     */
    public long getTimeRemaining() {
        int timer = 180000;         //3 minutes for the game in milliseconds
        long timeElapsed = System.currentTimeMillis() - startTime;
        long timeRemaining = timer - timeElapsed + bonusTime;
        long timeInSeconds = timeRemaining / 1000;
        long seconds = timeInSeconds % 60;
        long minutes = timeInSeconds / 60;
        if (seconds < 0 || minutes < 0) {   //so negative numbers don't show
            seconds = 0;
            minutes = 0;
        }
        return timeInSeconds;
    }
    /**
     * Add time to the game timer, print out remaining time.
     * @param time to add to the game timer.
     */
    public void addBonusTime(int time) {
        if (time == 0) {
            System.out.println("No bonus time was added to the timer.");
            printTimeRemaining();
        } else {
            System.out.println(time +  " seconds were added to the timer!");
            time = time * 1000;         //convert to milliseconds
            bonusTime += time;
            printTimeRemaining();
        }
    }
    /**
     * Prints a strings saying how long is left on the game timer.
     */
    public void printTimeRemaining() {
       long timeInSeconds = getTimeRemaining();
       long seconds = timeInSeconds % 60;
       long minutes = timeInSeconds / 60;
       System.out.println("Time until the oxygen supply runs out: " + minutes 
       +  " minutes and " + seconds + " seconds.");
    }
}
