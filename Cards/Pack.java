import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

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
        boolean valid = myPack.validPack(myPack.getNumberOfPlayers(),myPack.getPackFile());
    
    }

    public int getNumberOfPlayers(){
        return nbOfPlayers;
    }

    public String getPackFile(){
        return packFile;
    }
    
    public Boolean validPack(int players,String fileName){
        File myFile = new File(fileName);
        int lines = 0;
        try {
            Scanner myReader = new Scanner(myFile);
            if (myFile.exists()){
                while (myReader.hasNextLine()) {
                    lines++;
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
                myReader.close();
                if (lines == 8*players){
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
