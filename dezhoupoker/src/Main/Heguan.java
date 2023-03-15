package Main;
import Card.*;


public class Heguan {
    //公选牌
    public card[] FiledCard=new card[5];


    public Heguan() {
    }

    public card getFiledCard(int i) {
        return FiledCard[i];
    }

    public String nowFiledCard(int stages){
        String nowFiledCard = "";
        if(stages>=2){
            for (int i = 0; i < 3; i++) {
                nowFiledCard+=FiledCard[i].printCard();
            }
            if (stages>=3) {
                nowFiledCard += FiledCard[3].printCard();
                if(stages==4||stages==5){
                    nowFiledCard+=FiledCard[4].printCard();
                }
            }

        }

        return nowFiledCard;
    }

    public void setFiledCard(card[] card) {
        this.FiledCard = card;
    }
}

