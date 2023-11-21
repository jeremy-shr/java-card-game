import java.util.concurrent.*;
import java.util.ArrayList;

public class Deck {

    private int deckNum;
    private ConcurrentLinkedQueue<Card> deckContent;
    private static ArrayList<Deck> allDecks = new ArrayList<>();

    public Deck(int number) {
        this.deckNum = number;
        this.deckContent = new ConcurrentLinkedQueue<>();
        allDecks.add(this);
    }

    public int getDeckNum() {
        return this.deckNum;
    }

    public ConcurrentLinkedQueue<Card> getDeckContent() {
        return this.deckContent;
    }

    public void addToDeck(Card card) {
        deckContent.offer(card);
    }

    public Card drawCard() {
        return deckContent.poll();
    }

    public static ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

}
