import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Pack {
    private int nbOfPlayers;
    private String packFileName;
    private Queue<Card> packCards;

    public Pack(int n, String location) {
        this.nbOfPlayers = n;
        this.packFileName = location;

        File myFile = new File(location);
        boolean valid = this.validPack(n, location);
        if (!valid) {
            throw new Error("Pack is not valid");
        }
        Queue<Card> packCards = new LinkedList<>();

        try {
            Scanner myReader = new Scanner(myFile);
            if (myFile.exists()) {
                String line;
                while ((line = myReader.nextLine()) != null) {
                    packCards.add(new Card(line));
                }
                myReader.close();
                this.packCards = packCards;
            }
        } catch (IOException e) {
            System.out.println("Error reading the file");
        }
    }

    public void distributeCards() {
        int n = this.nbOfPlayers;
        while ((this.packCards.size() > 4 * n)) {
            Card pickedCard = packCards.poll();
            // TODO: Give picked card to each player in round-robin fashion until each has
            // 4.
        }
        // TODO: Split remaining cards into 4 seperate decks.
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

    public Boolean validPack(int players, String fileName) {
        File myFile = new File(fileName);
        int lines = 0;
        try {
            Scanner myReader = new Scanner(myFile);
            if (myFile.exists()) {
                while (myReader.hasNextLine()) {
                    lines++;
                }
                myReader.close();
                if (lines == 8 * players) {
                    return true;
                }
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File has not been found at given location");
        }
        return false;
    }
}
