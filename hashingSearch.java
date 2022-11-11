import java.util.Scanner;
import java.util.stream.IntStream;

public class hashingSearch {
    public static void main(String[] args) {
        //Array Declaration
        String[] authorArray = new String[280000];
        Scanner scan = new Scanner(System.in);

        //Loops menu until Exit is pressed.
        boolean endMenu = false;
        while (!endMenu) {
            System.out.println("Authoren Suche mit Hash...");
            System.out.println("Authoren Hinzufügen oder Suchen?");

            System.out.println("A - Suchen");
            System.out.println("B - Hinzufügen");
            System.out.println("V - Kollisionen Suchen");
            System.out.println("X - Program beenden.");

            //Target/Author Input
            String menuChoice = scan.nextLine();

            //Author Suchen
            if (menuChoice.equals("A") || menuChoice.equals("a")) {
                System.out.println("Insert search Criteria: ");
                String target = scan.nextLine();
                int finalHash = hashMethod(target);
                try{
                    for (int i = 0; i < authorArray.length; i++) {
                        if (authorArray[i].equals(target)) {
                            System.out.println("Author " + target + " befindet sich in der Array mit der Index: " + i);
                        } else if (authorArray[finalHash] == null) {
                            System.out.println("Dieser Author befindet sich nicht in der Liste...");
                        }
                    }
                } catch (Exception e) {
                        System.out.println("Da der Author nicht in der Array ist, ein Exception wurde gefunden.");
                        System.out.println("Bitte fügen Sie den Author oder Suchen Sie erneut...");
                }

            //Author Hinzufugen
            } else if (menuChoice.equals("B") || menuChoice.equals("b")) {
                //User Input for Author Name
                System.out.println("- Authoren Hinzufügen -");
                System.out.println("Name der Author: ");
                String target = scan.nextLine();

                //Turns name into Hash with the Hash method.
                int finalHash = hashMethod(target);

                //Check if the Index finalHash in the authorArray is Empty:
                    //If yes then:  Fills it with the Author at Index = finalHash
                    //else:         Tries to find the next empty Index to put the Author in.
                if(authorArray[finalHash] == null) {
                    authorArray[finalHash] = target;
                    System.out.println("Author " + target + " wurde an der stelle " + finalHash + " hinzugefügt.");
                } else {
                    boolean indexIsFree = false;
                    while (!indexIsFree){
                        for(int i = finalHash; i < authorArray.length; i++)
                            if(authorArray[finalHash] == null){
                                indexIsFree = true;
                                authorArray[i] = target;
                                System.out.println("Author " + target + " wurde an der stelle " + i + " hinzugefügt.");
                        } else {
                                System.out.println("Kein freies Index gefunden, Author könnte nicht gespeichert werden.");
                            }
                    }
                }
            //Ends the Program
            } else if (menuChoice.equals("X") ||menuChoice.equals("x")){
                System.out.println("Program wird beendet. Adios!");
                endMenu = true;
            //Checks for existing Collision within the Array (Already Existing Authors)
            } else if (menuChoice.equals("V") || menuChoice.equals("v)")){
                System.out.println("Kollisionen werden gesucht...");
                int collisionCounter = 0;

                //Size is 280k in worst case that every entry causes a collision
                String[][] collisionArray = new String[2800000][2];
                for(int x = 0; x < authorArray.length ; x++){
                    for (int z = x+1; z < authorArray.length; z++){
                        if (z == authorArray.length - 1){
                            //lowers Z by 1 once it reaches the length of the array to avoid a OutOfBounds Exception
                            z--;
                            continue;
                        }
                        if(authorArray[x].equals(authorArray[z])) {
                            //Adds a collision to the counter.
                            collisionCounter++;
                            for (int i = 0; i < 280000; i++){
                                for(int y = 0; y <= 2; y++){
                                    if (y == 2){ y=0; continue; }
                                    String hash1Collision = authorArray[x];
                                    String hash2Collision = authorArray[z];
                                    collisionArray[i][y] = hash1Collision;
                                    collisionArray[i][y+1] = hash2Collision;

                                }
                            }
                        }
                    }
                }
                System.out.println("Gefundene Kollisionen: " + collisionCounter);
                for (int a = 0; a <= 280000; a++){
                    for(int b = 0; b <= 2; b++){
                        if(b == 2){ b = 0; continue;}
                        System.out.println(collisionArray[a][b]);
                    }
                }
            }
        }
    }
    public static int hashMethod(String target){
        String targetLC = target.toLowerCase();

        char[] targetChar = targetLC.toCharArray();

        int[] tempHashArray = new int[targetChar.length];

        for (int i = 0; i <= targetChar.length - 1; i++) {

            int targetHashValue = ((int)(targetChar[i]) - 96);
            tempHashArray[i] = targetHashValue;
        }

        int tempHashSum = IntStream.of(tempHashArray).sum();

        //final Hash
        return (((tempHashSum)*100* targetChar.length)%280000);
    }
}
