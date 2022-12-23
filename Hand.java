public class Hand{
    private Card[] Hand;
    public void setHand(Card[] Hand){this.Hand=Hand;}
    public Card[] getHand(){return Hand;}
    public int sumPoints(int pisti){
    int sum=0;
    for (Card c : Hand) {
      sum+=c.getValue();     
    }
    for(int i=0;i<pisti;i++){
      sum+=10;
    }
    return sum;
    }
}