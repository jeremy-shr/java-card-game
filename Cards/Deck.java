import java.util.concurrent.*;
import java.util.ArrayList;

public class Deck {

    private int deckNum;
    private ConcurrentLinkedQueue<Card> deckContent;
    private static ArrayList<Deck> allDecks = new ArrayList<>();

    // Deck constructor
    public Deck(int number) {
        this.deckNum = number;
        this.deckContent = new ConcurrentLinkedQueue<>();
        allDecks.add(this);
    }

    // Method adding a Card to the end of the Deck queue
    public void addToDeck(Card card) {
        deckContent.offer(card);
    }

    // Method that takes and returns the first card of the Deck (head)
    public Card drawCard() {
        return deckContent.poll();
    }

    // Get methods

    public int getDeckNum() {
        return this.deckNum;
    }

    public ConcurrentLinkedQueue<Card> getDeckContent() {
        return this.deckContent;
    }

    public static ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

}
