import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Pack {
    private int nbOfPlayers;
    private String packFileName;
    private Queue<Card> packCards;

    public Pack(int n, String location) {
        this.nbOfPlayers = n;
        this.packFileName = location;
        this.packCards = new LinkedList<>();
    }

    public int getNumberOfPlayers() {
        return this.nbOfPlayers;
    }

    public String getPackFileName() {
        return this.packFileName;
    }

    public Queue<Card> getPackCards() {
        return this.packCards;
    }

    public static Boolean validPack(int players, String fileName) {
        File myFile = new File(fileName);
        int lines = 0;
        try {
            BufferedReader myReader = new BufferedReader(new FileReader(fileName));
            if (myFile.exists()) {
                while (myReader.readLine() != null) {
                    lines++;
                }
                myReader.close();
                if (lines == 8 * players) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File has not been found at given location");
        } catch (IOException e) {
            System.out.println("File has an error regagding IO ");
        }
        return false;
    }

    public void readPack(String fileName) {
        File myFile = new File(fileName);
        try {
            if (myFile.exists()) {
                Scanner myReader = new Scanner(myFile);
                while (myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    packCards.add(new Card(line));
                }
                myReader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading the file");
        }
    }

    public void distributeCards() {
        int n = this.nbOfPlayers;
        // Create a list to store references to the players
        List<Player> players = new ArrayList<>();
        List<Deck> decks = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Player player = new Player(i);
            Deck deck = new Deck(i);
            players.add(player);
            decks.add(deck);
        }
        int playerIndex = 0;
        int deckIndex = 0;

        while (packCards.size() > 4 * n) {
            Card pickedCard = packCards.poll();
            players.get(playerIndex).addToHand(pickedCard, 0);
            playerIndex = (playerIndex + 1) % n;
        }

        while (packCards.size() > 0) {
            Card pickedCard = packCards.poll();
            decks.get(playerIndex).addToDeck(pickedCard);
            deckIndex = (deckIndex + 1) % n;
        }

    }
}
