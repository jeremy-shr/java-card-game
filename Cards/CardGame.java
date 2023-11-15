import java.util.Scanner;

public class CardGame {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter the number of players:");
        int nbOfPlayers = userInput.nextInt();
        userInput.close();

        Scanner userInput2 = new Scanner(System.in);
        System.out.println("Please enter location of pack to load:");
        String packFileName = userInput2.nextLine();
        userInput2.close();

        Pack gamePack = new Pack(nbOfPlayers, packFileName);
        gamePack.distributeCards();
    }
}
