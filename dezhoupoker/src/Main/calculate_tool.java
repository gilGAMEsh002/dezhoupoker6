package Main;

import Card.*;
import Player.*;


import java.util.Arrays;
import java.util.Scanner;

import static Card.cardSet.judge67Cards2;
import static Card.cardSet.judgeTwoCards;
import static Main.main.fapai;

public class calculate_tool {

    public static void probability(player player1,player player2,Heguan heguan){
        int rounds=0,p1_win=0,p2_win=0,tie=0;
        int strong=0,weak=0,weaker=0,error1=0,error2=0;
        int One=0,Two=0,Three=0,Four=0,Five=0,Six=0,Seven=0,Eight=0,Nine=0,Ten=0;
        while (rounds<10000000){
            fapai(player1,player2,heguan);

            int player1_weight = judge67Cards2(player1.handCard, heguan.FiledCard);
            int player2_weight = judge67Cards2(player2.handCard, heguan.FiledCard);

            if(player1_weight==player2_weight){
                //平局的牌型
                tie++;
                switch (player1_weight){
                    case 1->One++;
                    case 2->Two++;
                    case 3->Three++;
                    case 4->Four++;
                    case 5->Five++;
                    case 6->Six++;
                    case 7->Seven++;
                    case 8->Eight++;
                    case 9->Nine++;
                    case 10->Ten++;
                    case 0->error2++;
                }
                switch (judgeTwoCards(player1.handCard)){
                    case 3->strong++;
                    case 2->weak++;
                    case 1->weaker++;
                    case 0->error1++;
                }
            } else if (player1_weight>player2_weight) {
                //p1胜的牌型
                p1_win++;
                switch (player1_weight){
                    case 1->One++;
                    case 2->Two++;
                    case 3->Three++;
                    case 4->Four++;
                    case 5->Five++;
                    case 6->Six++;
                    case 7->Seven++;
                    case 8->Eight++;
                    case 9->Nine++;
                    case 10->Ten++;
                    case 0->error2++;
                }
                switch (judgeTwoCards(player1.handCard)){
                    case 3->strong++;
                    case 2->weak++;
                    case 1->weaker++;
                    case 0->error1++;
                }
            }else {
                //p2胜的牌型
                p2_win++;
                switch (player2_weight){
                    case 1->One++;
                    case 2->Two++;
                    case 3->Three++;
                    case 4->Four++;
                    case 5->Five++;
                    case 6->Six++;
                    case 7->Seven++;
                    case 8->Eight++;
                    case 9->Nine++;
                    case 10->Ten++;
                    case 0->error2++;
                }
                switch (judgeTwoCards(player2.handCard)){
                    case 3->strong++;
                    case 2->weak++;
                    case 1->weaker++;
                    case 0->error1++;
                }
            }

            rounds++;

        }

        System.out.println("p1_win:"+p1_win);
        System.out.println("p2_win:"+p2_win);
        System.out.println("tie:"+tie);
        System.out.println("strong:"+(double)strong/10000000);
        System.out.println("weak:"+(double)weak/10000000);
        System.out.println("weaker:"+(double)weaker/10000000);
        System.out.println("高牌:"+(double)One/10000000);
        System.out.println("一对:"+(double)Two/10000000);
        System.out.println("两对:"+(double)Three/10000000);
        System.out.println("三条:"+(double)Four/10000000);
        System.out.println("顺子:"+(double)Five/10000000);
        System.out.println("同花:"+(double)Six/10000000);
        System.out.println("葫芦:"+(double)Seven/10000000);
        System.out.println("四条:"+(double)Eight/10000000);
        System.out.println("同花顺:"+(double)Nine/10000000);
        System.out.println("皇家同花顺:"+(double)Ten/10000000);
        System.out.println("error1:"+error1);
        System.out.println("error2:"+error2);

    }

    public static int checkStraight(card[] cards) {
        // 先对牌进行排序
        Arrays.sort(cards);

        int missing = 0;  // 记录需要的牌的数目
        for (int i = 0; i < cards.length - 1; i++) {
            // 如果有对子或以上的牌，则不能组成顺子
            if (cards[i].number == cards[i+1].number) {
                return 0;
            }

            // 如果有一张牌的数字比另一张牌的数字大于1，则需要插入一张牌
            if (cards[i+1].number - cards[i].number > 1) {
                missing++;
                // 如果需要插入的牌的数目超过2，则无法组成顺子，返回0
                if (missing > 2) {
                    return 0;
                }
            }
        }

        return missing;
    }

    public static void main(String[] args) {
        card[] testCardSet= new card[5];
        playerAI player1 = new playerAI();
        playerAI player2 = new playerAI();
        Heguan heguan = new Heguan();


        //各个牌型的胜率,包含平局
        probability(player1,player2,heguan);





    }
}