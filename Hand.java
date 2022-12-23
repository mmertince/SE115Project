public class Hand{
    private Card[] Hand;
    public void setHand(Card[] Hand){this.Hand=Hand;}
    public Card[] getHand(){return Hand;}
    public int sumPoints(){
    int sum=0;
    for (Card c : Hand) {
      sum+=c.getValue();     
    }
    return sum;
    }
}