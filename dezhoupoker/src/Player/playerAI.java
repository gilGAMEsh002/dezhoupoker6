package Player;

import Card.*;

import java.util.Objects;

public class playerAI extends player {

    //1.bet 2.call 3.raise 4.check 5.fold
    int roundAI = 1;
    static int PreflopRound_Raise = 0;
    static int FlopBet = 0;
    static int FlopRaise = 0;
    static int TurnBet = 0;
    static int TurnRaise = 0;
    static int RiverBet = 0;
    static int RiverRaise = 0;
    /* 1.基础策略：根据手牌的类型，对应的跟注、加注或弃牌。
     * 2.闲家策略：根据对手的行为和手牌的类型，决定是否跟注、加注或弃牌。
     * 3.数据驱动策略：根据历史游戏数据，分析每一种游戏情境下的最优决策。
     * 4.反规则策略：在某些情况下，与基础策略相反的决策可能更有效。
     */


    public String getPreflop_choice(String P1choice,int rounds){

        if(rounds==roundAI){

        }else{
            PreflopRound_Raise = 0;
            FlopBet = 0;
            FlopRaise = 0;
            TurnBet = 0;
            TurnRaise = 0;
            RiverBet = 0;
            RiverRaise = 0;
            roundAI++;
        }
        String AIchoice = "";
        int Number_DIFF = Math.abs(this.handCard[0].number-this.handCard[1].number);
        int Color_DIFF = Math.abs(this.handCard[0].color- this.handCard[1].color);


        if(Number_DIFF==0&&PreflopRound_Raise<3){
            AIchoice = "3";     //进行三次加注
            PreflopRound_Raise++;
        } else if(Color_DIFF==0&&Number_DIFF<5&&PreflopRound_Raise<1){
            AIchoice = "3";     //进行一次加注
            PreflopRound_Raise++;
        } else {
            //根据对手行为选择跟注或过牌
            if("2".equals(P1choice)||"4".equals(P1choice)){
                AIchoice = "4";     //他Call我Check
            } else if ("3".equals(P1choice)) {
                AIchoice = "2";     //他Raise我Call
            }
        }


        return AIchoice;
    }

    public String getFlop_choice(String P1choice,card[] FlopCard,String warning){
        String AIchoice = "";
        card[] cardAll = new card[5];
        int k = 0;
        for(int i=0;i<5;i++){
            if(i<2){
                cardAll[i] = this.handCard[i];
            }else {
                cardAll[i] = FlopCard[k++];
            }
        }
        int weight = 0;
        weight = cardSet.judgeCards2(cardAll);

        if(P1choice=="3")
            FlopRaise++;

        if (FlopBet<1){
            FlopBet++;
            return "1";
        } else if (warning!="") {
            switch (warning){

                case "筹码不足,无法Bet":
                case "筹码不足,无法Raise":
                    return "4";
                case "筹码不足,无法Call":
                    return "5";

            }
        } else if ((cardSet.judgeTwoCards(this.handCard)<3)&&weight==2&&FlopRaise<3) {
            FlopRaise++;
            return "3";
        } else if (weight==3&&FlopRaise<3) {
            FlopRaise++;
            return "3";
        } else if (weight==4&&FlopRaise<3) {
            FlopRaise++;
            return "3";
        } else if (weight==5&&FlopRaise<4) {
            FlopRaise++;
            return "3";
        } else if (weight==6&&FlopRaise<4) {
            FlopRaise++;
            return "3";
        } else if (weight==7&&FlopRaise<4) {
            FlopRaise++;
            return "3";
        } else if (weight==8&&FlopRaise<5) {
            FlopRaise++;
            return "3";
        } else if (weight==9&&FlopRaise<6) {
            FlopRaise++;
            return "3";
        } else if (weight==10&&FlopRaise<7) {
            FlopRaise++;
            return "3";
        }

        //根据对手行为选择跟注或过牌
        if("2".equals(P1choice)){
            AIchoice = "4";     //他Call我Check
        } else if ("3".equals(P1choice)) {
            AIchoice = "2";     //他Raise我Call
        }



        return AIchoice;
    }


    public String getTurn_choice(String P1choice, card[] TurnCard, String warning){

        String AIchoice = "";

        int weight = 0;
        card[] card4 = new card[4];
        for (int i = 0; i < 4; i++) {
            card4[i] = TurnCard[i];
        }


        weight = cardSet.judge67Cards2(this.handCard,card4);

        if(P1choice=="3")
            TurnRaise++;

        if (TurnBet<1){
            TurnBet++;
            return "1";
        } else if (!Objects.equals(warning, "")) {
            switch (warning){

                case "筹码不足,无法Bet":
                case "筹码不足,无法Raise":
                    return "4";
                case "筹码不足,无法Call":
                    return "5";

            }
        } else if ((cardSet.judgeTwoCards(this.handCard)<3)&&weight==2&&TurnRaise<3) {
            TurnRaise++;
            return "3";
        } else if (weight==3&&TurnRaise<3) {
            TurnRaise++;
            return "3";
        } else if (weight==4&&TurnRaise<3) {
            TurnRaise++;
            return "3";
        } else if (weight==5&&TurnRaise<4) {
            TurnRaise++;
            return "3";
        } else if (weight==6&&TurnRaise<4) {
            TurnRaise++;
            return "3";
        } else if (weight==7&&TurnRaise<4) {
            TurnRaise++;
            return "3";
        } else if (weight==8&&TurnRaise<5) {
            TurnRaise++;
            return "3";
        } else if (weight==9&&TurnRaise<6) {
            TurnRaise++;
            return "3";
        } else if (weight==10&&TurnRaise<7) {
            TurnRaise++;
            return "3";
        }

        //根据对手行为选择跟注或过牌
        if("2".equals(P1choice)){
            AIchoice = "4";     //他Call我Check
        } else if ("3".equals(P1choice)) {
            AIchoice = "2";     //他Raise我Call
        }



        return AIchoice;

    }

    public String getRiver_choice(String P1choice, card[] RiverCard, String warning){

        String AIchoice = "";

        int weight = 0;
        card[] card4 = new card[4];
        for (int i = 0; i < 4; i++) {
            card4[i] = RiverCard[i];
        }


        weight = cardSet.judge67Cards2(this.handCard,card4);

        if(P1choice=="3")
            RiverRaise++;

        if (RiverBet<1){
            RiverBet++;
            return "1";
        } else if (!Objects.equals(warning, "")) {
            switch (warning){

                case "筹码不足,无法Bet":
                case "筹码不足,无法Raise":
                    return "4";
                case "筹码不足,无法Call":
                    return "5";

            }
        } else if ((cardSet.judgeTwoCards(this.handCard)<3)&&weight==2&&RiverRaise<3) {
            RiverRaise++;
            return "3";
        } else if (weight==3&&RiverRaise<3) {
            RiverRaise++;
            return "3";
        } else if (weight==4&&RiverRaise<3) {
            RiverRaise++;
            return "3";
        } else if (weight==5&&RiverRaise<4) {
            RiverRaise++;
            return "3";
        } else if (weight==6&&RiverRaise<4) {
            RiverRaise++;
            return "3";
        } else if (weight==7&&RiverRaise<4) {
            RiverRaise++;
            return "3";
        } else if (weight==8&&RiverRaise<5) {
            RiverRaise++;
            return "3";
        } else if (weight==9&&RiverRaise<6) {
            RiverRaise++;
            return "3";
        } else if (weight==10&&RiverRaise<7) {
            RiverRaise++;
            return "3";
        }

        //根据对手行为选择跟注或过牌
        if("2".equals(P1choice)){
            AIchoice = "4";     //他Call我Check
        } else if ("3".equals(P1choice)) {
            AIchoice = "2";     //他Raise我Call
        }


        return AIchoice;

    }


}