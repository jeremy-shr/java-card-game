import java.util.Scanner;

public class Pack {
    private int nbOfPlayers;
    private String packFile;

    public Pack(int n,String location) {
        this.nbOfPlayers = n;
        this.packFile = location;
    }
    
    
    public static void main(String[] args){
        
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter the number of players:");   
        int nbOfPlayers = userInput.nextInt();
    
        Scanner userInput2 = new Scanner(System.in);
        System.out.println("Please enter location of pack to load:");   
        String packFile = userInput2.nextLine();
       
        Pack myPack = new Pack(nbOfPlayers, packFile);
    
    }
    
    public int getNumberOfPlayers(){
        return nbOfPlayers;
    }
    
    public String getPackFile(){
        return packFile;
    }

}
