import java.util.Scanner;
import java.util.Random;

public class Pisti{
   
    public static void main(String[] args) { 
        Scanner sc=new Scanner(System.in);
        Random rd=new Random(System.currentTimeMillis());
        Card[] deck=new Card[52];
        Card[] computerTook=new Card[52];
        Card[] playerTook=new Card[52];
        String lastTaker=new String();
        int computersChoice;
        int pistiPlayer=0;
        int pistiComputer=0;
        for(int i=0;i<playerTook.length;i++){
            playerTook[i]=new Card();
        }
        Card[] computerHand=new Card[4];
        Card[] playerHand=new Card[4];
        Card[] floorCards=new Card[52];
        deck=createDeck();
    

        //deck preparing
        deck=createDeck();
        deck=shuffleDeck(deck);
        deck=cutDeck(deck);

        
        boolean round=true;
        boolean game=true;
        boolean reDealToFloor=true;
        boolean playerHandLacked=true;

        //making the floor cards and reseting deck
       // floorCards=dealingCard(deck);
       // deck=newDeck(deck);
       
        //game starts here for player
        while(game){
        /*  if(playerHandLacked){
            computerHand=dealingCard(deck);
            deck=newDeck(deck);
            playerHand=dealingCard(deck);
            deck=newDeck(deck);
         } */
         if(deck.length==0){break;}
            
             if(reDealToFloor){
                floorCards=dealingCard(deck);
                deck=newDeck(deck);
            } 
            if(deck.length==0){break;}
            System.out.println("new round");
            round=true;
            while(round){
              if((playerHandLacked)&&(deck.length!=0)){
                computerHand=dealingCard(deck);
                deck=newDeck(deck);
              } if((playerHandLacked)&&(deck.length!=0)){
                playerHand=dealingCard(deck);
                deck=newDeck(deck);
             }


              playerHandLacked=false;
              if(floorCards.length>0&&floorCards!=null){
                System.out.println("floor cards are");
                for(Card display : floorCards){
                  if(display!=null){
                    System.out.println(display.getSymbol()+display.getNumber());
                  }
                }
              }

                System.out.println("players turn");
                
                System.out.println("your cards are ");
                for(int i=0;i<playerHand.length;i++){
                  if(playerHand[i].getSymbol()!=null){
                    System.out.println(i+1+" "+playerHand[i].getSymbol()+playerHand[i].getNumber());
                  }
                }
            
                System.out.println("please pick a card from numbers in the left");
                String picknumber=sc.nextLine();
                boolean check=true;
                int pickedcartno=0;
                while(check){
                 for (int j=1;j<=playerHand.length;j++){
                    if(Integer.parseInt(picknumber)==j){
                        pickedcartno=j;
                        check=false;
                    }
                }
                 if (check){
                    System.out.println("please enter valid number");
                    continue;
                }
              }
              floorCards=addFloorDeck(floorCards,playerHand[pickedcartno-1]);
              playerHand=updatePlayerCards(playerHand,playerHand[pickedcartno-1]);
              

              if(floorCards[0]!=null&&floorCards[1]!=null){
              if(floorCards[0].getNumber().equals(floorCards[1].getNumber())||floorCards[0].getNumber()=="J"){
                if(checkPisti(floorCards)){
                  System.out.println("YOU MADE A PISTI");
                  pistiPlayer++;
                }
                playerTook=collectCards(floorCards, playerTook);
                floorCards=emptyArr(floorCards);
                lastTaker="player";
                System.out.println("player took");
                for (Card card : playerTook) {
                  if(card.getSymbol()!=null){
                  System.out.println(card.getSymbol()+card.getNumber());
                }
              }
                //reDealToFloor=true;
                //round=false;
                //continue;
              }
            }

              /*if((reDealToFloor)&&(deck.length!=0)){
                floorCards=dealingCard(deck);
                deck=newDeck(deck);
                reDealToFloor=false;
              } */

          

              System.out.println("computers cards are");
              for(int i=0;i<computerHand.length;i++){
                  System.out.println(i+1+" "+computerHand[i].getSymbol()+computerHand[i].getNumber());
                }
              

                if(floorCards[0]!=null){
                computersChoice=computerAI(computerHand, floorCards[0]);
                }
                else{
                  computersChoice=rd.nextInt(computerHand.length);
                }
               System.out.println(computersChoice);
               floorCards=addFloorDeck(floorCards,computerHand[computersChoice]);
               computerHand=updatePlayerCards(computerHand,computerHand[computersChoice]);
               if(floorCards[0]!=null&&floorCards[1]!=null){
                if(floorCards[0].getNumber().equals(floorCards[1].getNumber())||floorCards[0].getNumber()=="J"){
                  if(checkPisti(floorCards)){
                    System.out.println("COMPUTER MADE A PISTI");
                    pistiComputer++;
                  }
                
                 computerTook=collectCards(floorCards, playerTook);
                 floorCards=emptyArr(floorCards);
                 lastTaker="computer";
                 System.out.println("computer took");
                 for (Card card : computerTook) {
                   if(card.getNumber()!=null){
                   System.out.println(card.getSymbol()+card.getNumber());
                 }
               }
              }
                //reDealToFloor=true;
                //round=false;
                
              }
              /*if(reDealToFloor&&deck.length!=0){
                floorCards=dealingCard(deck);
                deck=newDeck(deck);
                reDealToFloor=false;
              }*/

              
               //computerHand=updatePlayerCards(computerHand,computerHand[computersChoice]);
                if(playerHand.length==0&&computerHand.length==0){
                    round=false;
                    playerHandLacked=true;
                }
                reDealToFloor=false;
                if(deck.length==0&&computerHand.length==0){break;}

                





        
        }
       }

       playerTook=collectCards(playerHand, playerTook);
       computerTook=collectCards(computerHand, playerTook);

       if(lastTaker=="player"){
        playerTook=collectCards(floorCards, playerTook);
        floorCards=emptyArr(floorCards);
       }
       else if(lastTaker=="computer"){
        computerTook=collectCards(floorCards, computerTook);
        floorCards=emptyArr(floorCards);
       }

       Hand playerTookHand= new Hand();
       playerTookHand.setHand(playerTook);
       Hand computerTookHand= new Hand();
       computerTookHand.setHand(computerTook);
       System.out.println("calculating points");

       for (Card card : computerTook) {
        if(card.getNumber()!=null){
        System.out.println(card.getSymbol()+card.getNumber());
      }
    }
       System.out.println("computer made "+pistiComputer+" pisti");
       for (Card card : playerTook) {
        if(card.getSymbol()!=null){
        System.out.println(card.getSymbol()+card.getNumber());
      }
    }
       System.out.println("player made "+pistiPlayer+" pisti");



       System.out.println("computer has "+ computerTookHand.sumPoints(pistiComputer));
       System.out.println("player has "+ playerTookHand.sumPoints(pistiPlayer));
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
                deck[i*13+j].setValue(1);
                int temp2=j+1;
                if (j==0){
                  deck[i*13+j].setNumber("A");
                  deck[i*13+j].setValue(1);
                }else if(j==10){
                  deck[i*13+j].setNumber("J");
                  deck[i*13+j].setValue(1);
                }else if(j==11){
                  deck[i*13+j].setNumber("Q");
                  deck[i*13+j].setValue(1);
                }else if(j==12){
                  deck[i*13+j].setNumber("K");
                  deck[i*13+j].setValue(1);
                }else if(j==1){
                  deck[i*13+j].setNumber(String.valueOf(2));
                  deck[i*13+j].setValue(2);
                }else if(j==9){
                  deck[i*13+j].setNumber(String.valueOf(10));
                  deck[i*13+j].setValue(3);
                }else{ 
                  deck[i*13+j].setNumber(String.valueOf(temp2));
                  deck[i*13+j].setValue(1);
                }
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
                int c=deck[slctCard].getValue();
                shfDeck[i].setSymbol(a);
                shfDeck[i].setNumber(b);
                shfDeck[i].setValue(c);
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
            int c=deck[i].getValue();
            cutDeck[i+26].setSymbol(a);
            cutDeck[i+26].setNumber(b);
            cutDeck[i+26].setValue(c);
        }
           else{ 
              String a=deck[i].getSymbol();
              String b=deck[i].getNumber();
              int c=deck[i].getValue();
              cutDeck[i-26].setSymbol(a);  
              cutDeck[i-26].setNumber(b);
              cutDeck[i-26].setValue(c);
         }
    }
    return cutDeck;
  }

//deals card to appropiate place
  public static Card[] dealingCard(Card[] deck){
    Card[] dealedCards;
    if (deck.length>=4){
      dealedCards=new Card[4];
      for(int i=0;i<4;i++){
        dealedCards[i]=new Card();
        String a=deck[i].getSymbol();
        String b=deck[i].getNumber();
        int c=deck[i].getValue();
        dealedCards[i].setSymbol(a);
        dealedCards[i].setNumber(b); 
        dealedCards[i].setValue(c);
    }
    }
    else{
      dealedCards=new Card[deck.length];
    for(int i=0;i<deck.length;i++){
        dealedCards[i]=new Card();
        String a=deck[i].getSymbol();
        String b=deck[i].getNumber();
        int c=deck[i].getValue();
        dealedCards[i].setSymbol(a);
        dealedCards[i].setNumber(b); 
        dealedCards[i].setValue(c);
    }
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
        int c=deck[i].getValue();
        newDeck[i-4].setSymbol(a);
        newDeck[i-4].setNumber(b);
        newDeck[i-4].setValue(c);
    }
    return newDeck;
  }

  //adds the cart which is thrown by the player to the floor cards
  public static Card[] addFloorDeck(Card[] floorCards,Card thrownCard){
    Card[] newFloorCards=new Card[floorCards.length+1];
    System.arraycopy(floorCards, 0, newFloorCards, 1, floorCards.length);
    newFloorCards[0]=new Card();
    newFloorCards[0].setSymbol(thrownCard.getSymbol());
    newFloorCards[0].setNumber(thrownCard.getNumber());
    newFloorCards[0].setValue(thrownCard.getValue());
    /*String a=new String();
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
    */
    return newFloorCards;

  }

  // removes the thrown card from players hand
  public static Card[] updatePlayerCards(Card[] playerCards,Card thrownCard){
    Card[] updPlayerCards=new Card[playerCards.length-1];

    for (int i=0;i<updPlayerCards.length;i++){
          updPlayerCards[i]=new Card();
    }

    for(int i=0;i<updPlayerCards.length;i++){
        if(playerCards[i].getNumber()==thrownCard.getNumber()&&playerCards[i].getSymbol()==thrownCard.getSymbol()){
          for(int j=i;j<updPlayerCards.length;j++){
            updPlayerCards[j].setNumber(playerCards[j+1].getNumber());
            updPlayerCards[j].setSymbol(playerCards[j+1].getSymbol());
            updPlayerCards[j].setValue(playerCards[j+1].getValue());
            
          }
          break;
        }
          else{
            updPlayerCards[i].setSymbol(playerCards[i].getSymbol());
            updPlayerCards[i].setNumber(playerCards[i].getNumber());
            updPlayerCards[i].setValue(playerCards[i].getValue());
          }
      }


     
       
        return updPlayerCards;
          

  }

  //adds cart which are taken to the players inventory to count score
   public static Card[] collectCards(Card[] floorCards,Card[] playerTook ){
    Card[] newPlayerTook=new Card[playerTook.length+floorCards.length];
    for(int i=0;i<52;i++){
      newPlayerTook[i]=new Card();
    }
    
     for(int i=0;i<floorCards.length;i++){
        //newPlayerTook[i]=new Card();
        if(floorCards[i]!=null){
        String a=floorCards[i].getSymbol();
        String b=floorCards[i].getNumber();
        int c=floorCards[i].getValue();
        newPlayerTook[i].setSymbol(a);
        newPlayerTook[i].setNumber(b);
        newPlayerTook[i].setValue(c);
     }
    }

     for(int i=floorCards.length;i<newPlayerTook.length;i++){
        newPlayerTook[i]=new Card();
        String  a=playerTook[i-floorCards.length].getSymbol();
        String b=playerTook[i-floorCards.length].getNumber();
        int c=playerTook[i-floorCards.length].getValue();
        newPlayerTook[i].setSymbol(a);
        newPlayerTook[i].setNumber(b);
        newPlayerTook[i].setValue(c);

     }

       return newPlayerTook;

  }

  //smart playing for computer
  public static int computerAI(Card[] computerHand,Card floorCards){
    Random rd=new Random(System.currentTimeMillis());
    int determineCard=0;
    for(int i=0;i<computerHand.length;i++){
        if (computerHand[i].getNumber().equals(floorCards.getNumber())){
            determineCard=i;
           
        }
         else{
            determineCard=rd.nextInt(computerHand.length);
            
         }
    }
 
    return determineCard;

  }
  
  
//empty array
public static Card[] emptyArr(Card[] cardArr){
  Card[] emptyArrr=new Card[cardArr.length];
   for(Card c:cardArr){
    c=new Card();
  } 
  return emptyArrr;

}

//checks pisti

public static boolean checkPisti(Card[] floorCards){
  while(floorCards[1]!=null&&floorCards[2]==null){
  if (floorCards[0].getNumber().equals(floorCards[1].getNumber())){
    return true;}
  else{return false;}
  }
  return false;

  }
}
