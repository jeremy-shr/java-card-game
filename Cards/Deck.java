import java.util.concurrent.*;
import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

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

    // Define method adding a Card to the end of the Deck queue
    public void addToDeck(Card card) {
        deckContent.offer(card);
    }

    // Define method that takes and returns the first card of the Deck (head)
    public Card drawCard() {
        return deckContent.poll();
    }

    // Define method to write deck contents to a file with a given pathname
    public void writeToDeck(String pathname) {
        try {
            File myFile = new File(pathname);
            myFile.createNewFile();
            FileWriter myWriter = new FileWriter(pathname);
            myWriter.write("deck" + deckNum + " contents: ");
            while (!this.getDeckContent().isEmpty())
                for (int i = 0; i < this.getDeckContent().size(); i++) {
                    myWriter.append(this.getDeckContent().poll().getFaceValue() + " ");
                }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Encountered an IO error");
        }
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
