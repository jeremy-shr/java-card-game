import java.util.Scanner;

public class CardGame {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        try {
            System.out.println("Please enter the number of players:");
            int nbOfPlayers = userInput.nextInt();
            userInput.nextLine();

            System.out.println("Please enter location of pack to load:");
            String packFileName = userInput.nextLine();

            Pack gamePack = new Pack(nbOfPlayers, packFileName);
            gamePack.readPack(packFileName);
            System.out.println(gamePack.getPackCards());
            gamePack.distributeCards();

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            userInput.close();
        }
    }
}
