import java.util.concurrent.*;
import java.util.ArrayList;

public class Player implements Runnable{
    private int playerNum;
    private ConcurrentLinkedQueue<Card> playerHand;
    private static ArrayList<Player> allPlayers = new ArrayList<>();

    public Player(int number) {
        this.playerNum = number;
        this.playerHand = new ConcurrentLinkedQueue<>();
        allPlayers.add(this);
    }

    public void run(){
        //TODO 
    }

    public int getPlayerNum() {
        return this.playerNum;
    }

    public ConcurrentLinkedQueue<Card> getPlayerHand() {
        return this.playerHand;
    }

    public void addToHand(Card card) {
        playerHand.offer(card);
    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public void startThread(){
        Thread thread = new Thread(this);
        thread.start();  
    }
}
