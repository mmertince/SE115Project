import java.util.Scanner;
import java.util.Random;

public class Pisti{
    public static void main(String[] args) { 
        Scanner sc=new Scanner(System.in);
        Random rd=new Random(System.currentTimeMillis());
        Card[] deck=new Card[52];
        deck=createDeck();
        for(int i=0;i<deck.length;i++){
            System.out.println(deck[i].getSymbol()+""+deck[i].getNumber());
        }


        
    }
     public static Card[] createDeck(){
            Card[] deck=new Card[52];
        char[] suits={'s','c','h','d'};
        for (int i=0;i<4;i++){
            for(int j=0;j<13;j++){
                char temp=suits[i];
                deck[i*13+j]=new Card();
                deck[i*13+j].setSymbol(temp);
                int temp2=j+1;
                if (j==0)deck[i*13+j].setNumber("A");
                else if(j==10)deck[i*13+j].setNumber("J");
                else if(j==11)deck[i*13+j].setNumber("Q");
                else if(j==12)deck[i*13+j].setNumber("K");
                   else deck[i*13+j].setNumber(String.valueOf(temp2));
        }
    }
    return deck;
}
}