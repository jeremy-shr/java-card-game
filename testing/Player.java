import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Player implements Runnable {
    private String pathName;
    private int playerNum;
    private ArrayList<Card> playerHand;
    private Deck drawDeck;
    private Deck discardDeck;
    private static ArrayList<Player> allPlayers = new ArrayList<>();
    private Thread thread;
    public static volatile boolean stopFlag = false;
    private static volatile int winnerNum;

    // Player class constructor
    public Player(int number) {
        this.playerNum = number;
        this.playerHand = new ArrayList<>();
        allPlayers.add(this);
    }

    // Define the run method for the Runnable interface
    @Override
    public void run() {
        loadGame();
        int lPointer = 0;

        // Defining the player's discard deck and draw deck
        int numOfPlayers = Deck.getAllDecks().size();
        drawDeck = Deck.getAllDecks().get(getPlayerNum() - 1);
        if (playerNum == numOfPlayers) {
            discardDeck = Deck.getAllDecks().get(0);
        } else {
            discardDeck = Deck.getAllDecks().get(playerNum);
        }

        // Main game loop
        while (!this.winner() && !stopFlag) {
            if (lPointer == 4) {
                lPointer = 0;
            }
            String newCard = getPlayerHand().get(lPointer).getFaceValue();

            // Check for card preference
            if (newCard.equals(Integer.toString(this.playerNum))) {
                lPointer++;
            } else if (drawDeck.getDeckContent().size() != 0) {
                replaceCard(lPointer);
                lPointer++;
            }
        }

        // Handle winner found case
        handleGameEnd();
    }

    // Define method to replace cards as a single atomic action
    public synchronized void replaceCard(int index) {
        // Discard card
        Card discardedCard = playerHand.get(index);
        discardDeck.addToDeck(discardedCard);
        removeCard(index);

        // Draw card
        Card drawnCard = drawDeck.drawCard();
        addToHand(drawnCard, index);

        // Output result of action to output file
        String line1 = "player " + playerNum + " draws a " + drawnCard.getFaceValue() + " from deck " + playerNum;
        String line2 = "player " + playerNum + " discards a " + discardedCard.getFaceValue() + " to deck "
                + discardDeck.getDeckNum();
        String line3 = "player " + playerNum + " current hand is" + presentHand();
        writeToFile(line1, line2, line3, playerNum);
    }

    // Define method for adding a card to a hand in a specific index
    public void addToHand(Card card, int index) {
        playerHand.add(index, card);
    }

    // Define method checking if a player has won
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
        // Flag to signal a winner has been found to all threads
        Player.stopFlag = true;
        stopAllThreads();
        return true;
    }

    // Define method to handle thread behaviour when a winner is found
    public void handleGameEnd() {
        Boolean isWinner = this.winner();
        int num = this.playerNum;
        if (isWinner) {
            Player.winnerNum = num;
        }

        // Write final state of the game to output file
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(this.pathName, true);
            if (isWinner) {
                myWriter.write("player " + num + " wins\n");
                myWriter.write("player " + num + " exits\n");
                myWriter.write("player " + num + " final hand:" + presentHand());
                System.out.println("player " + num + " wins");
            } else {
                myWriter.write("player " + winnerNum + " has informed player " + num + " that player " + winnerNum
                        + " has won\n");
                myWriter.write("player " + num + " exits\n");
                myWriter.write("player " + num + " hand:" + presentHand());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Encountered an IO error");
        }
        String deckPathName = "deck" + getPlayerNum() + "_output.txt";
        this.drawDeck.writeToDeck(deckPathName);
    }

    // Define method to load game prerequisites: every thread must be created and
    // have checked if it is a winner.
    public void loadGame() {
        // Sleep to ensure all threads are initialised before checking if each is an
        // instant winner
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        winner();

        // Sleep to allow all threads to check if they are a winner before starting the
        // game.
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    // Define method to create / overwrite player output files when game starts
    public void createOutputFile() {
        this.pathName = "player" + getPlayerNum() + "_output.txt";
        try {
            File myFile = new File(pathName);
            myFile.createNewFile();
            FileWriter myWriter = new FileWriter(pathName);
            myWriter.write("player " + getPlayerNum() + " initial hand" + presentHand() + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Encountered an IO error");
        }
    }

    // Define method used to write messages to Player output files
    public void writeToFile(String message, String message2, String message3, int playerNum) {
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(this.pathName, true);
            myWriter.write(message + "\n");
            myWriter.write(message2 + "\n");
            myWriter.write(message3 + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Encountered an IO error");
        }
    }

    // Define method to format Cards in hand into a String
    public String presentHand() {
        String hand = "";
        for (int i = 0; i < playerHand.size(); i++) {
            hand = hand + " " + playerHand.get(i).getFaceValue();
        }
        return hand;
    };

    public void removeCard(int index) {
        playerHand.remove(index);
    }

    // Thread management methods

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void stopThread() {
        thread.interrupt();
    }

    public static void stopAllThreads() {
        for (Player player : getAllPlayers()) {
            player.stopThread();
        }
    }

    // Get methods

    public int getPlayerNum() {
        return this.playerNum;
    }

    public ArrayList<Card> getPlayerHand() {
        return this.playerHand;
    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }
}
