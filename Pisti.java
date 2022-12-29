import java.util.Scanner;

import javax.sql.rowset.spi.SyncResolver;
import javax.xml.transform.Templates;

import java.util.Random;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Formatter;
import java.io.File;

public class Pisti{
    public static void main(String[] args) { 
      try{
      File file=new File("highScoreList.txt");
       if(!file.exists()){
        file.createNewFile();
       }
      }
       catch(IOException X){
        X.printStackTrace();
       }
      Scanner shower=null;
      System.out.println("HIGH SCORE LIST");
      System.out.println("");
      int kount=0;
      String[] showerString=new String[2];
    try{
      shower=new Scanner(Paths.get("highScoreList.txt"));
         while(shower.hasNextLine()){
          
          kount++;
          showerString=shower.nextLine().split("-");
          if(showerString.length!=1){
           System.out.printf("%02d%s%s%s-%s\n",kount," <> ",showerString[0], " points ",showerString[1]);
          }
       }
      }
      catch(IOException l){
        l.printStackTrace();
      }
       finally{shower.close();}
      System.out.println("");
         
        Scanner sc=new Scanner(System.in);
        Scanner reader=null;
        User Player=new User();
        Random rd=new Random(System.currentTimeMillis());
        Card[] deck=new Card[52];
        Card[] computerTook=new Card[52];
        Card[] playerTook=new Card[52];
        String lastTaker=new String();
        int computersChoice;
        int pistiPlayer=0;
        int pistiComputer=0;
        int counterComputer=0;
        int counterPlayer=0;
        int computerPoints=0;
        int playerPoints=0;
        int pickedcartno=0;
        int numFloorCards=0;
        for(int i=0;i<playerTook.length;i++){
            playerTook[i]=new Card();
        }
        Card[] computerHand=new Card[4];
        Card[] playerHand=new Card[4];
        Card[] floorCards=new Card[52];
        

        System.out.println("PLEASE ENTER YOUR NAME");
        String playerName=sc.nextLine();
    

        //deck preparing
        deck=createDeck();
        deck=shuffleDeck(deck);
        deck=cutDeck(deck);

        
        boolean round=true;
        boolean game=true;
        boolean reDealToFloor=true;
        boolean playerHandLacked=true;
        boolean tryCatch=true;
        String picknumber=new String();


        System.out.println("GAME STARTED!!!");
        while(game){
 
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
              tryCatch=true;
              //
              
              for(int i=0;i<6;i++){
                System.out.println("");
                for(int j=0;j<12;j++){
                  System.out.print("-");
                }
              }
              System.out.println("");
              //
              if(floorCards.length>0&&floorCards[0]!=null){
                //System.out.println("floor cards are");
                System.out.println("");
                System.out.print("last card on the floor is :");
                numFloorCards=0;
              for(Card display : floorCards){
                  if(display!=null){
                     numFloorCards++;
                  }
                }
              
                System.out.print(floorCards[0].getSymbol()+"-"+floorCards[0].getNumber());
              }
              System.out.println("");
              System.out.println("floor has "+numFloorCards+" card");
                System.out.println("");
                System.out.println("PLAYERS TURN");
                System.out.println("");
                
                System.out.println("your cards are :");
                for(int i=0;i<playerHand.length;i++){
                  if(playerHand[i].getSymbol()!=null){
                    System.out.println(i+1+" "+playerHand[i].getSymbol()+"-"+playerHand[i].getNumber());
                  }
                }
            
                System.out.println("please pick a card from numbers in the left");
                while(tryCatch){
                  picknumber=sc.nextLine();
                try {
                Integer.parseInt(picknumber);
                tryCatch=false;
                } catch(Exception ex){
                  System.err.println("please enter a valid value");
                  System.err.println("");
                  System.err.println("your cards are :");
                  for(int i=0;i<playerHand.length;i++){
                    if(playerHand[i].getSymbol()!=null){
                      System.err.println(i+1+" "+playerHand[i].getSymbol()+"-"+playerHand[i].getNumber());
                    }
                  }
                  continue;
                }
            
                boolean check=true;
                pickedcartno=0;
                while(check){
                 for (int j=1;j<=playerHand.length;j++){
                    if(Integer.parseInt(picknumber)==j){
                        pickedcartno=j;
                        check=false;
                    }
                }
                 if (check){
                    System.out.println("please enter valid number");
                    check=false;
                    tryCatch=true;
                }
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
                System.out.println("PLAYER TOOK THE CARDS");
                /*for (Card card : playerTook) {
                  if(card!=null){
                  if(card.getSymbol()!=null){
                  System.out.println(card.getSymbol()+card.getNumber());
                }
              }
            }*/
                
              }
            }

          

          

              /*System.out.println("computers cards are");
              for(int i=0;i<computerHand.length;i++){
                  System.out.println(i+1+" "+computerHand[i].getSymbol()+computerHand[i].getNumber());
                }
              */

                if(floorCards[0]!=null){
                computersChoice=computerAI(computerHand, floorCards[0]);
                }
                else{
                  computersChoice=rd.nextInt(computerHand.length);
                }
          
               System.out.println("computer choose :"+computerHand[computersChoice].getSymbol()+"-"+computerHand[computersChoice].getNumber());
               floorCards=addFloorDeck(floorCards,computerHand[computersChoice]);
               computerHand=updatePlayerCards(computerHand,computerHand[computersChoice]);
               if(floorCards[0]!=null&&floorCards[1]!=null){
                if(floorCards[0].getNumber().equals(floorCards[1].getNumber())||floorCards[0].getNumber()=="J"){
                  if(checkPisti(floorCards)){
                    System.out.println("COMPUTER MADE A PISTI");
                    pistiComputer++;
                  }
                
                 computerTook=collectCards(floorCards, computerTook);
                 floorCards=emptyArr(floorCards);
                 lastTaker="computer";
                 System.out.println("");
                 System.out.println("COMPUTER TOOK THE CARDS");
                 numFloorCards=0;
                 /*for (Card card : computerTook) {
                  if(card!=null){
                   if(card.getNumber()!=null){
                   System.out.println(card.getSymbol()+card.getNumber());
                 }
                }
               
              }*/ 
              }
               
                
              }
    
                if(playerHand.length==0&&computerHand.length==0){
                    round=false;
                    playerHandLacked=true;
                }
                reDealToFloor=false;
                if(deck.length==0&&computerHand.length==0){break;}

                





        
        }
       }
//game ended
    for(int i=0;i<4;i++){
      System.out.println("");
      for(int j=0;j<12;j++){
        System.out.print("-");
      }
    }
       System.out.println("");
       System.out.println("GAME HAS ENDED");
       System.out.println("");

          playerTook=collectCards(playerHand, playerTook);
          computerTook=collectCards(computerHand, computerTook);

          if(lastTaker=="player"){
           playerTook=collectCards(floorCards, playerTook);
           floorCards=emptyArr(floorCards);
           System.out.println("player took the last cards so the remaining cards will go to the player");
          }
          else if(lastTaker=="computer"){
           computerTook=collectCards(floorCards, computerTook);
           floorCards=emptyArr(floorCards);
           System.out.println("computer took the last cards so the remaining cards will go to the computer");
          }
          System.out.println("");

          Hand playerTookHand= new Hand();
          playerTookHand.setHand(playerTook);
          Hand computerTookHand= new Hand();
          computerTookHand.setHand(computerTook);
          System.out.println("CALCULATING POINTS");
       System.out.println("");
       System.out.println("cards that computer took :");
       for (Card card : computerTook) {
        if(card!=null){
        if(card.getNumber()!=null){
          counterComputer++;
        System.out.println(card.getSymbol()+card.getNumber());
      }
    }
    }
       System.out.println("");
       System.out.println("computer made "+pistiComputer+" pisti");
       System.out.println("");
       System.out.println("cards that player took :");
       for (Card card : playerTook) {
        if(card!=null){
        if(card.getSymbol()!=null){
          counterPlayer++;
        System.out.println(card.getSymbol()+card.getNumber());
      }
    }
    }
       System.out.println("");
       System.out.println("player made "+pistiPlayer+" pisti");
       System.out.println("");

       computerPoints=computerTookHand.sumPoints(pistiComputer);
       playerPoints=playerTookHand.sumPoints(pistiPlayer);
       if(counterComputer>counterPlayer){
        System.out.println("computer has more cards addittional 3 points");
        computerPoints+=3;
       }
         else if(counterPlayer>counterComputer){
          System.out.println("player has more cards addittional 3 points");
          playerPoints+=3;
         }
         else{
          System.out.println("number of cards are equal for both players no addittional points");
         }
         System.out.println("");

 
     System.out.println("");


       System.out.println("");
       System.out.println("computer has "+ computerPoints);
       System.out.println("");
       System.out.println("player has "+ playerPoints);

       System.out.println("");
       if(computerPoints>playerPoints){
        System.out.println("COMPUTER HAS WON THE GAME");
       }
         else if(playerPoints>computerPoints){
          System.out.println("CONGRATS YOU WIN");
         }else{
          System.out.println("POINTS ARE EVEN ");
         }

         Player.setUser(playerName);
         Player.setUserPoints(playerPoints);
         int counting=0;
         User[] pointList=new User[10];//11
         String[] tempList=new String[2];
         try{
          reader=new Scanner(Paths.get("highScoreList.txt"));
          //Scanner reader2=new Scanner(Paths.get("highScoreList.txt"));
           //while(counting<10){
           //try{
            while(reader.hasNextLine()){
              try{
                if(counting<10){
            pointList[counting]=new User();
            tempList=reader.nextLine().split("-");
                
           
            if(tempList!=null){
              if(tempList.length!=1){
              if(tempList[1]!=null){
            pointList[counting].setUser(tempList[1]);
            pointList[counting].setUserPoints(Integer.parseInt(tempList[0]));
            counting++;
              }
            }
          }
          }
          }
        
        
           catch(Exception exc){
            exc.printStackTrace();
           }
          }
        }
         
         
           catch(IOException e){
            e.printStackTrace();
           }
           finally{
            if(reader!=null){
              reader.close();
            }
           }
           
           User tempUser=new User();
           int sortting=0;

           boolean sorting=true;

           for(int i=0;i<pointList.length;i++){
            if(pointList[i]==null){
              pointList[i]=new User();
              pointList[i].setUser(playerName);
              pointList[i].setUserPoints(playerPoints);
              break;
            } else if(pointList[9]!=null){
               if(pointList[9].getUserPoints()<playerPoints){
                pointList[9].setUserPoints(playerPoints);
                pointList[9].setUser(playerName);
               }
            }
           }

           for(int i=0;i<9;i++){
            if(pointList[i]!=null){//&&pointList[i+1]!=null){
              for(int j=i;j<10;j++){
                 if(pointList[j]!=null){
                  if(pointList[i].getUserPoints()<pointList[j].getUserPoints()){
                   tempUser.setUser(pointList[i].getUser());
                   tempUser.setUserPoints(pointList[i].getUserPoints());
                   pointList[i].setUser(pointList[j].getUser());
                   pointList[i].setUserPoints(pointList[j].getUserPoints());
                   pointList[j].setUser(tempUser.getUser());
                   pointList[j].setUserPoints(tempUser.getUserPoints());
                }
              }
            }
           }
          }
        
        
          /*else if(i==0){
            pointList[0]=new User();
            pointList[0].setUser(Player.getUser());
            pointList[0].setUserPoints(Player.getUserPoints());
          }*/
        

        Formatter f=null;
        for(User uk:pointList){
       
          int counter3=0;

        try {
          f = new Formatter("highScoreList.txt");
          for(User u:pointList){
            if(u!=null){
              if(u.getUser()!=null){
                counter3++;
          f.format("%d%s%s\n",u.getUserPoints(),"-",u.getUser());
                }
            }
          }
          }
        catch(Exception e){}
          finally{
            f.close();
          }
        }

        System.out.println("");
        System.out.println("NEW HIGH SCORE LIST");

        Scanner shower2=null;
        String[] showerString2=new String[2];
        int kount2=0;
        try{
            shower2=new Scanner(Paths.get("highScoreList.txt"));
             while(shower2.hasNextLine()){
              try{
                kount2++;
                showerString2=shower2.nextLine().split("-");
                if(showerString2.length!=1){
               System.out.printf("%02d%s%s%s-%s\n",kount2," <> ",showerString2[0], " points ",showerString2[1]);
              }
            }
                catch(Exception ok){
                  ok.printStackTrace();
                }
           }
          }
          catch(IOException l){
            l.printStackTrace();
          }
           finally{shower2.close();}
        
  
      sc.close();


          
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
    Card[] newPlayerTook=new Card[52];
    for(int i=0;i<52;i++){
      newPlayerTook[i]=new Card();
    }
    int counter1=0;
    int counter2=0;
    for(int i=0;i<floorCards.length;i++){
      if(floorCards[i]!=null){
        counter1++;
      }
    }
    for(int i=0;i<playerTook.length;i++){
      if(playerTook[i]!=null){
        counter2++;
      }
    }
    
    
     for(int i=0;i<counter1;i++){
        //newPlayerTook[i]=new Card();
        String a=floorCards[i].getSymbol();
        String b=floorCards[i].getNumber();
        int c=floorCards[i].getValue();
        newPlayerTook[i].setSymbol(a);
        newPlayerTook[i].setNumber(b);
        newPlayerTook[i].setValue(c);
    }

     for(int i=counter1;i<counter2;i++){
        newPlayerTook[i]=new Card();
        String  a=playerTook[i-counter1].getSymbol();
        String b=playerTook[i-counter1].getNumber();
        int c=playerTook[i-counter1].getValue();
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
           break;
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
