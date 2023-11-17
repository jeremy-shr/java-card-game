import java.util.concurrent.*;
 
public class Player {
    private int playerNum;
    private ConcurrentLinkedQueue<Card> playerHand; 

    public Player(int number){
        this.playerNum = number;
        this.playerHand = new ConcurrentLinkedQueue<>();
    }

    public int getPlayerNum(){
        return playerNum;
    }

    public ConcurrentLinkedQueue<Card> getPlayerHand(){
        return playerHand;
    }

    public void addToHand(Card card) {
        playerHand.offer(card);
    }

}
