import java.util.Scanner;

public class CardGame {

    // Define method used for validating user input
    static boolean checkInt(String s) {
        try {
            int i = Integer.parseInt(s);
            if (i <= 0) {
                return false;
            }
            return true;
        } catch (NumberFormatException er) {
            return false;
        }
    }

    // Define main method running on game start
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        // Input validation
        try {
            System.out.println("Please enter the number of players:");
            String nbOfPlayer = userInput.nextLine();

            // Handle case for incorrect value inputed for n of players
            while (!checkInt(nbOfPlayer)) {
                System.out.println("The number of players is not valid");
                System.out.println("Please enter the number of players:");
                nbOfPlayer = userInput.nextLine();
            }
            int nbOfPlayers = Integer.parseInt(nbOfPlayer);

            System.out.println("Please enter location of pack to load:");
            String packFileName = userInput.nextLine();
            boolean validate = Pack.validPack(nbOfPlayers, packFileName);

            // Handle case for incorrect file used to load the pack.
            while (!validate) {
                System.out.println("The file is not valid");
                System.out.println("Please enter location of pack to load:");
                packFileName = userInput.nextLine();
                validate = Pack.validPack(nbOfPlayers, packFileName);
            }
            userInput.close();

            // Instantiate a new pack and distribute cards to players and decks
            Pack gamePack = new Pack(nbOfPlayers, packFileName);
            gamePack.readPack(packFileName);
            gamePack.distributeCards();

            // Loop to start all threads and therefore start the game with all necessary
            // output files
            for (int i = 0; i < nbOfPlayers; i++) {
                Player.getAllPlayers().get(i).createOutputFile();
                Player.getAllPlayers().get(i).startThread();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
