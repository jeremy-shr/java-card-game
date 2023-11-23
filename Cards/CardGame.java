import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CardGame {

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

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        try {
            System.out.println("Please enter the number of players:");
            String nbOfPlayer = userInput.nextLine();

            while (!checkInt(nbOfPlayer)) {
                System.out.println("The number of players is not valid");
                System.out.println("Please enter the number of players:");
                nbOfPlayer = userInput.nextLine();
            }
            int nbOfPlayers = Integer.parseInt(nbOfPlayer);

            System.out.println("Please enter location of pack to load:");
            String packFileName = userInput.nextLine();
            boolean validate = Pack.validPack(nbOfPlayers, packFileName);

            while (!validate) {
                System.out.println("The file is not valid");
                System.out.println("Please enter location of pack to load:");
                packFileName = userInput.nextLine();
                validate = Pack.validPack(nbOfPlayers, packFileName);
            }

            Pack gamePack = new Pack(nbOfPlayers, packFileName);
            gamePack.readPack(packFileName);
            gamePack.distributeCards();

            for (int i = 0; i < nbOfPlayers; i++) {
                Player.getAllPlayers().get(i).createOutputFile();
                Player.getAllPlayers().get(i).startThread();
                if (Player.getAllPlayers().get(i).winner()){
                    System.out.println("player "+ Player.getAllPlayers().get(i).getPlayerNum() +" wins");
                    Player.stopAllThreads();
                }
            }
            
            
            /* 
            //PRINT DECKS CONTENT
            for (int k = 0; k < Deck.getAllDecks().size();k++){
               ConcurrentLinkedQueue<Card> pd = Deck.getAllDecks().get(k).getDeckContent();
               System.out.println("---p"+(k+1)+"---");
               while (!pd.isEmpty()) {
                Card deckCard = pd.poll();
                System.out.println(deckCard.getFaceValue());
               }
            }
            */


            /*
             * //PRINT PLAYERS HANDS
             * for (int j = 0; j < Player.getAllPlayers().size();j++){
             * ConcurrentLinkedQueue<Card> ph =
             * Player.getAllPlayers().get(j).getPlayerHand();
             * System.out.println("---p"+(j+1)+"---");
             * while (!ph.isEmpty()){
             * Card hand1 = ph.poll();
             * System.out.println(hand1.getFaceValue());
             * }
             * }
             */

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            userInput.close();
        }
    }
}
