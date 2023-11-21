import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;

public class Player implements Runnable {
    private int playerNum;
    private ArrayList<Card> playerHand;
    private Deck drawDeck;
    private Deck discardDeck;
    private static ArrayList<Player> allPlayers = new ArrayList<>();

    public Player(int number) {
        this.playerNum = number;
        this.playerHand = new ArrayList<>();
        allPlayers.add(this);
    }

    public void run() {
        int lPointer = 0;

        // Defining the player's discard and draw decks
        int numOfPlayers = Deck.getAllDecks().size();
        drawDeck = Deck.getAllDecks().get(playerNum - 1);
        if (playerNum == numOfPlayers) {
            discardDeck = Deck.getAllDecks().get(numOfPlayers - 1);
        } else {
            discardDeck = Deck.getAllDecks().get(playerNum);
        }

        while (!this.winner()) {
            if (this.playerHand.get(lPointer).getFaceValue() == Integer.toString(this.playerNum)) {
                lPointer++;
            } else {
                replaceCard(lPointer);
            }
        }
        // Declare winner and break
    }

    public synchronized void replaceCard(int index) {
        discardDeck.addToDeck(playerHand.get(index));
        playerHand.remove(index);
        addToHand(drawDeck.drawCard(), index);
    }

    public int getPlayerNum() {
        return this.playerNum;
    }

    public ArrayList<Card> getPlayerHand() {
        return this.playerHand;
    }

    public void addToHand(Card card, int index) {
        playerHand.add(index, card);
    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public void startThread() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public Boolean winner() {
        Card firstCard = playerHand.get(0);
        String firstCardVal = firstCard.getFaceValue();
        for (int i = 0; i < playerHand.size(); i++) {
            Card currentCard = playerHand.get(i);
            String currentCardVal = currentCard.getFaceValue();
            if (!currentCardVal.equals(firstCardVal)) {
                return false;
            }
        }
        return true;
    }

    public void createOutputFile() {
        String pathName = "player" + getPlayerNum() + "_output.txt";
        try {
            File myFile = new File(pathName);
            myFile.createNewFile();
            FileWriter myWriter = new FileWriter(pathName);
            myWriter.write("player " + getPlayerNum() + " initial hand " + playerHand.get(0) + " " + playerHand.get(1)
                    + " " + playerHand.get(2) + " " + playerHand.get(3));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("File has an error regarding IO ");
        }
    }
}
