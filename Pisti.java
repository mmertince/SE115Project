import java.util.Scanner;
import java.util.Random;

public class Pisti{
    public static void main(String[] args) { 
        Scanner sc=new Scanner(System.in);
        Random rd=new Random(System.currentTimeMillis());
        Card[] deck=new Card[52];
        Card[] computerTook=new Card[52];
        Card[] playerTook=new Card[52];
        for(int i=0;i<playerTook.length;i++){
            playerTook[i]=new Card();
        }
        Card[] computerHand=new Card[4];
        Card[] playerHand=new Card[4];
        Card[] floorCards=new Card[52];
        /*deck=createDeck();
        for(int i=0;i<deck.length;i++){
            System.out.println(deck[i].getSymbol()+"-"+deck[i].getNumber());
        }
        deck=shuffleDeck(deck);
        for(int i=0;i<deck.length;i++){
            System.out.println(deck[i].getSymbol()+""+deck[i].getNumber());
        }
        System.out.println("-");
        deck=cutDeck(deck);
        for(int i=0;i<deck.length;i++){
            System.out.println(deck[i].getSymbol()+""+deck[i].getNumber());
        }*/

        //game starts here
        deck=createDeck();
        deck=shuffleDeck(deck);
        deck=cutDeck(deck);
        boolean round=true;
        boolean game=true;
        boolean reDealToFloor=false;
        floorCards=dealingCard(deck);
        deck=newDeck(deck);
        while(game){
            computerHand=dealingCard(deck);
            deck=newDeck(deck);
            playerHand=dealingCard(deck);
            deck=newDeck(deck);
            if(reDealToFloor){
                floorCards=dealingCard(deck);
                deck=newDeck(deck);
            }
            round=true;
            while(round){
                System.out.println("players turn");
                PlayersTurn(playerHand, floorCards,playerTook,reDealToFloor);
                computerAI(computerHand, floorCards);

                





        
        }
      }
    }

    //creates deck
     public static Card[] createDeck(){
            Card[] deck=new Card[52];
        String[] suits={"s","c","h","d"};
        for (int i=0;i<4;i++){
            for(int j=0;j<13;j++){
                String temp=suits[i];
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

//shuffles deck
    public static Card[] shuffleDeck(Card[] deck){
        Random rd=new Random(System.currentTimeMillis());
        Card[] shfDeck=new Card[52];
        int[] arr=new int[52];
        boolean go=false;
        for(int i=0;i<52;i++){
            go=false;
            shfDeck[i]=new Card();
            int slctCard=rd.nextInt(52);
            arr[i]=slctCard;
            for(int j=0;j<i;j++){
                if(arr[j]==slctCard){
                    go=true;
                }
            }
            if(go){
                i--;
                continue;
            }
              else{
                String a=deck[slctCard].getSymbol();
                String b=deck[slctCard].getNumber();
                shfDeck[i].setSymbol(a);
                shfDeck[i].setNumber(b);
        }
    }
    return shfDeck;
  }

//cuts deck
  public static Card[] cutDeck(Card[] deck){
    Card[] cutDeck=new Card[52];
    for (int i=0;i<52;i++){
        cutDeck[i]=new Card();
    }
    for (int i=0;i<52;i++){
        if(i<26){
            String a=deck[i].getSymbol();
            String b=deck[i].getNumber();
            cutDeck[i+26].setSymbol(a);
            cutDeck[i+26].setNumber(b);
        }
           else{ 
              String a=deck[i].getSymbol();
              String b=deck[i].getNumber();
              cutDeck[i-26].setSymbol(a);  
              cutDeck[i-26].setNumber(b);
         }
    }
    return cutDeck;
  }

//deals card to appropiate place
  public static Card[] dealingCard(Card[] deck){
    Card[] dealedCards=new Card[4];
    for(int i=0;i<4;i++){
        dealedCards[i]=new Card();
        String a=deck[i].getSymbol();
        String b=deck[i].getNumber();
        dealedCards[i].setSymbol(a);
        dealedCards[i].setNumber(b); 
    }
    return dealedCards;
}

//returns the deck without the dealed cards
  public static Card[] newDeck(Card[] deck){
    Card[] newDeck=new Card[deck.length-4];
    for(int i=4;i<deck.length;i++){
        newDeck[i-4]=new Card();
        String a=deck[i].getSymbol();
        String b=deck[i].getNumber();
        newDeck[i-4].setSymbol(a);
        newDeck[i-4].setNumber(b);
    }
    return newDeck;
  }

  //adds the cart which is thrown by the player to the floor cards
  public static Card[] addFloorDeck(Card[] floorCards,Card thrownCard){
    Card[] newFloorCards=new Card[floorCards.length];
    String a=new String();
    String b=new String();
    for(int i=0;i<newFloorCards.length;i++){
        newFloorCards[i]=new Card();
        if(i==0){
            a=thrownCard.getSymbol();
            b=thrownCard.getNumber();
            floorCards[i].setSymbol(a);
            floorCards[i].setNumber(b);}
          else{
             a=floorCards[i-1].getSymbol();
             b=floorCards[i-1].getNumber();
             newFloorCards[i].setSymbol(a);
             floorCards[i].setNumber(b);
        }
    }
    return newFloorCards;

  }

  // removes the thrown card from players hand
  public static Card[] updatePlayerCards(Card[] playerCards,Card thrownCard){
    Card[] updPlayerCards=new Card[playerCards.length-1];
    for (int i=0;i<updPlayerCards.length;i++){
        updPlayerCards[i]=new Card();
    }

        if(playerCards[3]==thrownCard){
            for (int i=0;i<3;i++){
                String a=playerCards[i].getSymbol();
                String b=playerCards[i].getNumber();
                updPlayerCards[i].setSymbol(a);
                updPlayerCards[i].setNumber(b);
            }
        }

         else if(playerCards[0]==thrownCard){
            for (int i=1;i<4;i++){
                String a=playerCards[i].getSymbol();
                String b=playerCards[i].getNumber();
                updPlayerCards[i-1].setSymbol(a);
                updPlayerCards[i-1].setNumber(b);
            }
        }

         else if(playerCards[1]==thrownCard){
            String a1=playerCards[0].getSymbol();
            String b1=playerCards[0].getNumber();
            updPlayerCards[0].setSymbol(a1);
            updPlayerCards[0].setNumber(b1);
            for (int i=2;i<4;i++){
                String a=playerCards[i].getSymbol();
                String b=playerCards[i].getNumber();
                updPlayerCards[i-1].setSymbol(a);
                updPlayerCards[i-1].setNumber(b);
            }
        }

        else if(playerCards[2]==thrownCard){
            String a1=playerCards[3].getSymbol();
            String b1=playerCards[3].getNumber();
            updPlayerCards[2].setSymbol(a1);
            updPlayerCards[2].setNumber(b1);
            for (int i=0;i<2;i++){
                String a=playerCards[i].getSymbol();
                String b=playerCards[i].getNumber();
                updPlayerCards[i].setSymbol(a);
                updPlayerCards[i].setNumber(b);
            }
        }
        return updPlayerCards;
          

  }

  //adds cart which are taken to the players inventory to count score
   public static Card[] collectCars(Card[] floorCards,Card[] playerTook ){
    Card[] newPlayerTook=new Card[playerTook.length+floorCards.length];
    
     for(int i=0;i<floorCards.length;i++){
        newPlayerTook[i]=new Card();
        String a=floorCards[i].getSymbol();
        String b=floorCards[i].getNumber();
        newPlayerTook[i].setSymbol(a);
        newPlayerTook[i].setNumber(b);
     }

     for(int i=floorCards.length;i<newPlayerTook.length;i++){
        newPlayerTook[i]=new Card();
        String  a=playerTook[i-floorCards.length].getSymbol();
        String b=playerTook[i-floorCards.length].getNumber();
        newPlayerTook[i].setSymbol(a);
        newPlayerTook[i].setNumber(b);

     }

       return newPlayerTook;

  }

  //smart playing for computer
  public static void computerAI(Card[] computerHand,Card[] floorCards){
    Random rd=new Random(System.currentTimeMillis());
    for(int i=0;i<floorCards.length;i++){
        if (computerHand[i].getNumber()==floorCards[0].getNumber()){
            int determineCard=i;
            addFloorDeck(floorCards,computerHand[determineCard]);
            updatePlayerCards(computerHand,computerHand[determineCard]);
            collectCars(floorCards, computerHand);
        }
         else{
            int newdetermineCard=rd.nextInt(computerHand.length);
            addFloorDeck(floorCards,computerHand[newdetermineCard]);
            updatePlayerCards(computerHand,computerHand[newdetermineCard]);
         }
    }

  }
  
  
//game flow in players turn
  public static void PlayersTurn(Card[] playerHand,Card[] floorCards,Card[] playerTook,boolean reDealToFloor){
    Scanner sc=new Scanner(System.in);
    String a=new String();
    String b=new String();
    System.out.println("your cards are ");
    for(int i=0;i<playerHand.length;i++){
        a=playerHand[i].getSymbol();
        b=playerHand[i].getNumber();
        System.out.println(i+1+" "+a+b);
  }
    System.out.println("please pick a card from numbers in the left");
    String picknumber=sc.nextLine();
    boolean check=true;
    int pickedcartno=0;
    while(check){
     for (int i=1;i<=4;i++){
        if(Integer.parseInt(picknumber)==i){
            pickedcartno=i;
            check=false;
        }
    }
     if (check){
        System.out.println("please enter only 1,2,3 or 4");
    }
  }
  floorCards=addFloorDeck(floorCards,playerHand[pickedcartno-1]);
  playerHand=updatePlayerCards(playerHand,playerHand[pickedcartno-1]);
  if(floorCards[0].getNumber()==floorCards[1].getNumber()){
    playerTook=collectCars(floorCards, playerTook);
    reDealToFloor=true;
  }
}



}
