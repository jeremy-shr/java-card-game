import java.util.concurrent.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Iterator;
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

    public Boolean winner(){
        Card firstCard = playerHand.peek();
        String firstCardVal = firstCard.getFaceValue();
        Iterator<Card> iterator = this.getPlayerHand().iterator();

        while (iterator.hasNext()) {
            Card currentCard = iterator.next();
            String currentCardVal = currentCard.getFaceValue();
            if (!currentCardVal.equals(firstCardVal)){
                return false;
            }
        }
        return true;
    }

    public void createOutputFile(){
        String pathName = "player"+ getPlayerNum() +"_output.txt";
        Iterator<Card> iterator = this.getPlayerHand().iterator();
        ArrayList<String> handArray = new ArrayList<>(); 
        while (iterator.hasNext()) {
            String currentCard = iterator.next().getFaceValue();
            handArray.add(currentCard);
        }
        try{
            File myFile = new File(pathName);
            myFile.createNewFile();
            FileWriter myWriter = new FileWriter(pathName);
            myWriter.write("player "+getPlayerNum()+" initial hand "+handArray.get(0)+" "+handArray.get(1)+" "+handArray.get(2)+" "+handArray.get(3));
            myWriter.close();
        } catch (IOException e){
            System.out.println("File has an error regagding IO ");
        }
    }
}
