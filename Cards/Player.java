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
    private Thread thread;
    private volatile boolean stopFlag = false;
    private static ArrayList<Thread> allThreads = new ArrayList<>();

    public Player(int number) {
        this.playerNum = number;
        this.playerHand = new ArrayList<>();
        allPlayers.add(this);
    }

    public void run() {
        int lPointer = 0;
    
        // Defining the player's discard and draw decks
        int numOfPlayers = Deck.getAllDecks().size();
        drawDeck = Deck.getAllDecks().get(getPlayerNum() - 1);
        if (playerNum == numOfPlayers) {
            discardDeck = Deck.getAllDecks().get(0);
        } else {
            discardDeck = Deck.getAllDecks().get(playerNum);
        }
    
        while (!stopFlag && !Thread.currentThread().isInterrupted() && !this.winner()) {
            String newCard = getPlayerHand().get(lPointer).getFaceValue();
            if (newCard.equals(Integer.toString(this.playerNum))) {
                lPointer++;
            } else {
                replaceCard(lPointer);
            }
        }

        // Handle interruption after the loop
        if (Thread.currentThread().isInterrupted()) {
            stopThread();
        }
    }
    

    public synchronized void replaceCard(int index) {
        discardDeck.addToDeck(playerHand.get(index));
        playerHand.remove(index);
        addToHand(drawDeck.drawCard(), index);
        System.out.println("cards have been replaced for "+playerNum);
       
        try {
            if (discardDeck.getDeckContent().size() == 4 && drawDeck.getDeckContent().size() == 4) {
                for (Player player : Player.getAllPlayers()) {
                    synchronized (player) {
                        player.notifyAll();
                    }
                }
            }
            while(!stopFlag ){
                System.out.println(thread.getName()+" is now waiting");
                wait(); 
            }
        }catch (InterruptedException e){
        System.out.println("The thread "+getPlayerNum()+" has been interupted");
        }
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
    }

    public void stopThread(){
        stopFlag = true;
        thread.interrupt();
    }

    public static void stopAllThreads(){
        for (Player player : getAllPlayers()){
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
        stopAllThreads();
        return true;
    }

    public void createOutputFile() {
        String pathName = "player" + getPlayerNum() + "_output.txt";
        try {
            File myFile = new File(pathName);
            myFile.createNewFile();
            FileWriter myWriter = new FileWriter(pathName);
            myWriter.write("player " + getPlayerNum() + " initial hand " + playerHand.get(0).getFaceValue() + " " + playerHand.get(1).getFaceValue()
                    + " " + playerHand.get(2).getFaceValue() + " " + playerHand.get(3).getFaceValue());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("File has an error regarding IO ");
        }
    }
}
