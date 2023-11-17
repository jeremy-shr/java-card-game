import java.util.concurrent.*;

public class Deck{

    private int deckNum;
    private ConcurrentLinkedQueue<Card> deckContent; 

    public Deck(int number){
        this.deckNum = number;
        this.deckContent = new ConcurrentLinkedQueue<>();
    }

    public int getDeckNum(){
        return deckNum;
    }

    public ConcurrentLinkedQueue<Card> getDeckContent(){
        return deckContent;
    }

    public void addToDeck(Card card){
        deckContent.offer(card);
    }
}
