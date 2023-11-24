import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Player implements Runnable {
    private String pathName;
    private String deckPathName;
    private int playerNum;
    private ArrayList<Card> playerHand;
    private Deck drawDeck;
    private Deck discardDeck;
    private static ArrayList<Player> allPlayers = new ArrayList<>();
    private Thread thread;
    private static volatile boolean stopFlag = false;
    private static ArrayList<Thread> allThreads = new ArrayList<>();
    private volatile static int winnerNum;

    public Player(int number) {
        this.playerNum = number;
        this.playerHand = new ArrayList<>();
        allPlayers.add(this);
    }

    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {   
        }
        winner();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }

        int lPointer = 0;

        // Defining the player's discard and draw decks
        int numOfPlayers = Deck.getAllDecks().size();
        drawDeck = Deck.getAllDecks().get(getPlayerNum() - 1);
        if (playerNum == numOfPlayers) {
            discardDeck = Deck.getAllDecks().get(0);
        } else {
            discardDeck = Deck.getAllDecks().get(playerNum);
        }
        System.out.println("Draw deck for Player " + playerNum + " is of size " + drawDeck.getDeckContent().size());

        while (!this.winner() && !stopFlag) {
            if (lPointer == 4) {
                lPointer = 0;
            }
            String newCard = getPlayerHand().get(lPointer).getFaceValue();
            if (newCard.equals(Integer.toString(this.playerNum))) {
                lPointer++;
            } else if (drawDeck.getDeckContent().size() != 0) {
                replaceCard(lPointer);
                lPointer++;
            }
        }
        stopAllThreads();
        Boolean isWinner = this.winner();
        if (isWinner) {
            Player.winnerNum = playerNum;
        }
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(this.pathName, true);
            if (isWinner) {
                myWriter.write("player " + playerNum + " wins\n");
                myWriter.write("player " + playerNum + " exits\n");
                myWriter.write("player " + playerNum + " final hand:" + presentHand());
                System.out.println("player " + playerNum + " wins");
            } else {
                myWriter.write("player " + winnerNum + " has informed player " + playerNum + " that player " + winnerNum
                        + " has won\n");
                myWriter.write("player " + playerNum + " exits\n");
                myWriter.write("player " + playerNum + " hand:" + presentHand());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Encountered an IO error");
        }
        writeToDeck();

    }

    public synchronized void replaceCard(int index) {
        Card discardedCard = playerHand.get(index);
        discardDeck.addToDeck(discardedCard);
        System.out.println(playerNum + "Has discarded a card to deck " + discardDeck.getDeckNum());
        playerHand.remove(index);
        Card drawnCard = drawDeck.drawCard();
        addToHand(drawnCard, index);
        System.out.println(playerNum + "Has picked a card from deck " + drawDeck.getDeckNum());
        String line1 = "player " + playerNum + " draws a " + drawnCard.getFaceValue() + " from deck " + playerNum;
        String line2 = "player " + playerNum + " discards a " + discardedCard.getFaceValue() + " to deck "
                + discardDeck.getDeckNum();
        String line3 = "player " + playerNum + " current hand is" + presentHand();
        writeToFile(line1, line2, line3, playerNum);

        System.out.println(
                "Current Draw deck " + drawDeck.getDeckNum() + " is now of length" + drawDeck.getDeckContent().size());

        // try {
        // if (discardDeck.getDeckContent().size() == 4 &&
        // drawDeck.getDeckContent().size() == 4) {
        // for (Player player : Player.getAllPlayers()) {
        // synchronized (player) {
        // player.notifyAll();
        // }
        // }
        // }
        // while (!stopFlag) {
        // System.out.println(thread.getName() + " is now waiting");
        // wait();
        // }
        // } catch (InterruptedException e) {
        // System.out.println("The thread " + getPlayerNum() + " has been interupted");
        // }
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

    public static ArrayList<Thread> getAllThreads() {
        return allThreads;
    }


    public void startThread() {
        thread = new Thread(this);
        allThreads.add(thread);
        thread.start();
        System.out.println("Thread Created");
    }

    public void stopThread() {
        thread.interrupt();
    }

    public static void stopAllThreads() {
        for (Player player : getAllPlayers()) {
            player.stopThread();
        }
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
        Player.stopFlag = true;
        stopAllThreads();
        return true;
    }

    public void createOutputFile() {
        this.pathName = "player" + getPlayerNum() + "_output.txt";
        try {
            File myFile = new File(pathName);
            myFile.createNewFile();
            FileWriter myWriter = new FileWriter(pathName);
            myWriter.write("player " + getPlayerNum() + " initial hand" + presentHand()+ "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Encountered an IO error");
        }
    }

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

    public void writeToDeck(){
        this.deckPathName = "deck" + getPlayerNum() + "_output.txt";
        try {
            File myFile = new File(deckPathName);
            myFile.createNewFile();
            FileWriter myWriter = new FileWriter(deckPathName);
            myWriter.write("deck"+playerNum+" contents: ");
            while(!this.drawDeck.getDeckContent().isEmpty())
                for( int i = 0; i < this.drawDeck.getDeckContent().size(); i++){
                    myWriter.append(this.drawDeck.getDeckContent().poll().getFaceValue()+" ");
                }
            myWriter.close();
        }catch (IOException e){
            System.out.println("Encountered an IO error");
        }
    }

    public String presentHand() {
        String hand = "";
        for (int i = 0; i < playerHand.size(); i++) {
            hand = hand + " "+playerHand.get(i).getFaceValue();
        }
        return hand;
    };
}
