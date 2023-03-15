package Player;
import Card.card;

import java.sql.SQLOutput;

public class player {
    public int Blind;   //1,2分别为小盲与大盲
    public card[] handCard = new card[2];   //两张手牌
    public int chip = 20000;
    public int pay; //最小下注额为100
    public int lastpay;
    public boolean isFold = false;//用isFold来判断是否执行continue
    public String choice = "";


    public void setHandCard(card[] handCard) {
        this.handCard = handCard;
    }

    /**
     * Check,Call等方法的参数全部变为另一个player与stages(stages可能也会删除)
     */
    public void Bet(player player,int stages){
        this.pay = 100;
        this.chip = this.chip-100;
    }
    public void Check(player player,int stages){
        //过牌,相当于下注0
        //this.pay = 0;
        /*
         * 注释原因:不便于preflop后每个阶段下,第一个操作的人用call下注.
         *           再改进应该是,设置BET给每个阶段第一个下注的
         *
         * 改进!
         * */
    }
    //跟注，获取player1的pay
    public void Call(player player, int stages){
        lastpay = this.pay;             //记录上次下注额
        this.pay = player.pay;              //置本次下注额于对手下注额相同
//        this.chip = this.chip - this.pay;   //从玩家的筹码中扣掉下注  //错误的算法
        int difference = this.pay - lastpay;
        this.chip = this.chip - difference;
    }
    //加注
    public void Raise(player player,int stages){
        lastpay = this.pay;
        this.pay = 2*player.pay;
        int difference = this.pay - lastpay;
        this.chip = this.chip - difference;
    }
    //弃牌
    public void Fold(player player,int stages){
        this.isFold = true;
        System.out.println("player"+this.Blind+"弃牌,本局游戏结束");
    }


}