package Main;

import Player.*;
import Card.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static Card.cardSet.judge67Cards2;

public class main {

    static int stages = 1;
    static int rounds = 1;
    static int chipPool = 0;

    /*发牌
     * 给分别给p1,p2,两张手牌
     * 给h1五张场牌
     * */
    /*发牌*/
    public static void fapai(player p1, player p2, Heguan h1) {
        ArrayList<card> list = new ArrayList<>();
        int[] color = {1, 2, 3, 4};
        int[] number = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        //将52张牌存入牌盒
        for (int i = 0; i < color.length; i++) {
            for (int j = 0; j < number.length; j++) {
                list.add(new card(number[j], color[i]));
            }
        }
        //打乱扑克
        Collections.shuffle(list);
        //取头七张
        card[] t1 = new card[2];
        card[] t2 = new card[2];
        card[] t3 = new card[5];
        for (int i = 0; i < 9; i++) {
            if (i <= 1) {
                t1[i] = list.get(i);
            } else if (i > 1 && i <= 3) {
                t2[i - 2] = list.get(i);
            } else {
                t3[i - 4] = list.get(i);
            }
        }
        p1.setHandCard(t1);
        p2.setHandCard(t2);
        h1.setFiledCard(t3);
    }


    //随机给两名玩家中的一人赋大盲,另一人赋小盲
    /*1.preflop*/
    public static void preflop(playerAI player1, playerAI player2) {
        preflop:      //break label表示玩家都执行完操作了,可以结束preflop且能进去flop了
        while (true) {
            String P1choice = "";
            String P2choice = "";

            player1:         //break label1表示player1操作完了,该轮到player2了
            do {

                System.out.print("请player1选择:");
                if (player1.pay!=player2.pay&&(player1.chip - (player2.pay - player1.pay) >= 0)) {
                    System.out.print(" 2.Call ");
                }
                if (player1.chip - (2 * player2.pay - player1.pay) > 0) {
                    System.out.print(" 3.Raise ");
                }
                if (player1.pay == player2.pay) {
                    System.out.print("4.Check");
                }
                System.out.println("5.Fold");


                P1choice = scanner.next();

                switch (P1choice) {
                    case "2" -> {
                        if (player1.chip - (player2.pay - player1.pay) >= 0) {
                            player1.Call(player2, stages);
                            chipPool += player1.pay - player1.lastpay;
                            //NotDoFlag = false;
                            break player1;
                        } else {
                            System.out.println("筹码不足,无法Call,请按提示重新输入");

                        }
                    }
                    case "3" -> {
                        if (player1.chip - (2 * player2.pay - player1.pay) > 0) {
                            player1.Raise(player2, stages);
                            chipPool += player1.pay - player1.lastpay;
                            //NotDoFlag = false;
                            break player1;
                        } else {
                            System.out.println("筹码不足,无法Raise,请按提示重新输入");
                        }
                    }
                    case "4" -> {
                        if (player1.pay == player2.pay) {
                            player1.Check(player2, stages);
                            break player1;//最后应该是在player2那里的check结束
                        } else {
                            System.out.println("输入错误,请根据提示重新输入");
                        }
                    }
                    case "5" -> {
                        player1.Fold(player2, stages);
                        player2.chip += chipPool;       //player1认输,把筹码池的筹码都给player2
                        rounds++;
                        break preflop;
                    }
                    default -> {
                        System.out.println("输入错误,请根据提示重新输入");
                    }
                }
            } while (true);

            /**/
            //if (player1.isFold) return;     //判断是否直接结束,player2不再操作    //可以直接break label
            /**/


            player2:         //break label2表示player2操作完了,该准备结束preflop了
            while(true){
                System.out.print("请player2选择:");
                if(player1.pay!=player2.pay&&(player2.chip-(player1.pay-player2.pay)>=0)){
                    System.out.print(" 2.Call ");
                }
                if (player2.chip - (2 * player1.pay - player2.pay) > 0) {
                    System.out.print(" 3.Raise ");
                }
                if (player1.pay == player2.pay) {
                    System.out.print("4.Check");
                }
                System.out.println("5.Fold");

                P2choice = player2.getPreflop_choice(P1choice,rounds);
                System.out.println(P2choice);
                switch (P2choice) {
                    case "2" -> {
                        if (player1.pay!=player2.pay&&(player2.chip - (player1.pay - player2.pay) >= 0)) {
                            player2.Call(player1, stages);
                            chipPool += player2.pay - player2.lastpay;
                            break preflop;
                        } else {
                            System.out.println("筹码不足,无法Call,请按提示重新输入");
                        }
                    }
                    case "3" -> {
                        if (player2.chip - (2 * player1.pay - player2.pay) > 0) {
                            player2.Raise(player1, stages);
                            chipPool += player2.pay - player2.lastpay;
                            break player2;
                        } else {
                            System.out.println("筹码不足,无法Raise,请按提示重新输入");
                        }

                    }
                    case "4" -> {
                        if (player1.pay == player2.pay) {
                            player2.Check(player1, stages);
                            break preflop;
                        } else {
                            System.out.println("输入错误,请根据提示重新输入");
                        }
                    }
                    case "5" -> {
                        player2.Fold(player1, stages);
                        player1.chip += chipPool;       //player2认输,把筹码池的筹码都给player21
                        rounds++;
                        break preflop;
                    }
                    default -> {
                        System.out.println("输入错误,请根据提示重新输入");
                    }
                }

            }
            System.out.println("现在player1的筹码数:" + player1.chip);
            System.out.println("现在player2的筹码数:" + player2.chip);

            System.out.println("现在筹码池的筹码数:" + chipPool);


        }
        stages++;
    }

    /*2.flop*/
    public static void flop(playerAI player1, playerAI player2, Heguan heguan) {
        //player2先开始
        //player2做选择Bet,Call,Raise,Fold
        player1.pay =0;
        player2.pay =0;
        String P1choice = "";
        String P2choice = "";

        flop:
        while (true){
            String warning = "";

            player2:
            while (true){
                System.out.print("请player2选择:");
                if(player2.chip>=100&&(0==player2.pay)){
                    System.out.print("1.Bet");
                }else{
                    if(player1.pay!=player2.pay&&(player2.chip-(player1.pay-player2.pay)>=0)){//仅在player1raise时可以call
                        System.out.print(" 2.Call ");
                    }
                    if (player2.chip - (2 * player1.pay - player2.pay) > 0 ) {
                        System.out.print(" 3.Raise ");
                    }
                }
                if (player1.pay == player2.pay) {
                    System.out.print("4.Check");
                }
                System.out.println("5.Fold");
                //P2choice = scanner.next();
                P2choice = player2.getFlop_choice(P1choice, heguan.FiledCard,warning);
                System.out.println(P2choice);
                switch (P2choice) {
                    case "1" -> {
                        if (player2.chip>=100&&player2.pay==0) {
                            player2.Bet(player1,stages);
                            chipPool+=player2.pay;
                            break player2;
                        } else {
                            if(player2.chip<100){
                                warning = "筹码不足,无法Bet";
                            }
                            System.out.println(warning+"请按提示重新输入");
                        }
                    }
                    case "2" -> {
                        if (player1.pay!=player2.pay&&(player2.chip - (player1.pay - player2.pay) >= 0)) {
                            player2.Call(player1, stages);
                            chipPool += player2.pay - player2.lastpay;
                            break player2;
                        } else {
                            if(player2.chip - (player1.pay - player2.pay) < 0){
                                warning = "筹码不足,无法Call";
                            }
                            System.out.println(warning+"请按提示重新输入");
                        }
                    }
                    case "3" -> {
                        if ((player2.pay!=0)&&player2.chip - (2 * player1.pay - player2.pay) >= 0) {
                            player2.Raise(player1, stages);
                            chipPool += player2.pay - player2.lastpay;
                            break player2;
                        } else {
                            if(player2.chip - (2 * player1.pay - player2.pay) < 0){
                                warning = "筹码不足,无法Raise";
                            }
                            System.out.println(warning+"请按提示重新输入");
                        }

                    }
                    case "4" -> {
                        if (player1.pay == player2.pay) {
                            player2.Check(player1, stages);
                            break player2;
                        } else {
                            System.out.println("无法Check");
                        }
                    }
                    case "5" -> {
                        player2.Fold(player1, stages);
                        player1.chip += chipPool;       //player2认输,把筹码池的筹码都给player21
                        rounds++;
                        break flop;
                    }
                    default -> {
                        System.out.println("输入错误,请根据提示重新输入");
                    }
                }

            }

            player1:
            while (true){
                System.out.print("请player1选择:");
                if(0==player2.pay&&player1.chip>=100){
                    System.out.print("1.Bet");
                }else{
                    if(player1.pay!=player2.pay&&(player1.chip-(player2.pay-player1.pay)>=0)){//仅在player2raise时可以call
                        System.out.print(" 2.Call ");
                    }
                    if (player1.chip - (2 * player2.pay - player1.pay) > 0 ) {
                        System.out.print(" 3.Raise ");
                    }

                }
                if (player1.pay == player2.pay) {
                    System.out.print(" 4.Check");
                }
                System.out.println(" 5.Fold");
                P1choice = scanner.next();

                switch (P1choice) {
                    case "1" -> {
                        if (player1.chip >= 100) {
                            player1.Bet(player2,stages);
                            chipPool+=player1.pay;
                            break player1;
                        } else {
                            System.out.println("筹码不足,无法Bet,请按提示重新输入");
                        }
                    }
                    case "2" -> {
                        if (player1.pay!=player2.pay&&(player1.chip - (player2.pay - player1.pay) >= 0)) {
                            player1.Call(player2, stages);
                            chipPool += player1.pay - player1.lastpay;
                            break player1;
                        } else {
                            System.out.println("请按提示重新输入");
                        }
                    }
                    case "3" -> {
                        if (player1.chip - (2 * player2.pay - player1.pay) > 0) {
                            player1.Raise(player2, stages);
                            chipPool += player1.pay - player1.lastpay;
                            break player1;
                        } else {
                            System.out.println("筹码不足,无法Raise,请按提示重新输入");
                        }

                    }
                    case "4" -> {
                        if (player1.pay == player2.pay) {
                            player1.Check(player2, stages);
                            break flop;
                        } else {
                            System.out.println("输入错误,请根据提示重新输入");
                        }
                    }
                    case "5" -> {
                        player1.Fold(player2, stages);
                        player2.chip += chipPool;       //player1认输,把筹码池的筹码都给player2
                        rounds++;
                        break flop;
                    }
                    default -> {
                        System.out.println("输入错误,请根据提示重新输入");
                    }
                }

            }
        }

        stages++;
    }

    /*3.turn*/
    public static void turn(playerAI player1, playerAI player2, Heguan heguan) {
        //player2先开始
        //player2做选择Bet,Call,Raise,Fold
        player1.pay =0;
        player2.pay =0;
        String P1choice = "";
        String P2choice = "";

        flop:
        while (true){
            String warning = "";

            player2:
            while (true){
                System.out.print("请player2选择:");
                if(player2.chip>=100&&(0==player2.pay)){
                    System.out.print("1.Bet");
                }else{
                    if(player1.pay!=player2.pay&&(player2.chip-(player1.pay-player2.pay)>=0)){//仅在player1raise时可以call
                        System.out.print(" 2.Call ");
                    }
                    if (player2.chip - (2 * player1.pay - player2.pay) > 0 ) {
                        System.out.print(" 3.Raise ");
                    }
                }
                if (player1.pay == player2.pay) {
                    System.out.print("4.Check");
                }
                System.out.println("5.Fold");
                //P2choice = scanner.next();
                P2choice = player2.getTurn_choice(P1choice, heguan.FiledCard,warning);
                System.out.println(P2choice);
                switch (P2choice) {
                    case "1" -> {
                        if (player2.chip>=100&&player2.pay==0) {
                            player2.Bet(player1,stages);
                            chipPool+=player2.pay;
                            break player2;
                        } else {
                            if(player2.chip<100){
                                warning = "筹码不足,无法Bet";
                            }
                            System.out.println(warning+"请按提示重新输入");
                        }
                    }
                    case "2" -> {
                        if (player1.pay!=player2.pay&&(player2.chip - (player1.pay - player2.pay) >= 0)) {
                            player2.Call(player1, stages);
                            chipPool += player2.pay - player2.lastpay;
                            break player2;
                        } else {
                            if(player2.chip - (player1.pay - player2.pay) < 0){
                                warning = "筹码不足,无法Call";
                            }
                            System.out.println(warning+"请按提示重新输入");
                        }
                    }
                    case "3" -> {
                        if ((player2.pay!=0)&&player2.chip - (2 * player1.pay - player2.pay) >= 0) {
                            player2.Raise(player1, stages);
                            chipPool += player2.pay - player2.lastpay;
                            break player2;
                        } else {
                            if(player2.chip - (2 * player1.pay - player2.pay) < 0){
                                warning = "筹码不足,无法Raise";
                            }
                            System.out.println(warning+"请按提示重新输入");
                        }

                    }
                    case "4" -> {
                        if (player1.pay == player2.pay) {
                            player2.Check(player1, stages);
                            break player2;
                        } else {
                            System.out.println("无法Check");
                        }
                    }
                    case "5" -> {
                        player2.Fold(player1, stages);
                        player1.chip += chipPool;       //player2认输,把筹码池的筹码都给player21
                        rounds++;
                        break flop;
                    }
                    default -> {
                        System.out.println("输入错误,请根据提示重新输入");
                    }
                }

            }

            player1:
            while (true){
                System.out.print("请player1选择:");
                if(0==player2.pay&&player1.chip>=100){
                    System.out.print("1.Bet");
                }else{
                    if(player1.pay!=player2.pay&&(player1.chip-(player2.pay-player1.pay)>=0)){//仅在player2raise时可以call
                        System.out.print(" 2.Call ");
                    }
                    if (player1.chip - (2 * player2.pay - player1.pay) > 0 ) {
                        System.out.print(" 3.Raise ");
                    }

                }

                if (player1.pay == player2.pay) {
                    System.out.print(" 4.Check");
                }
                System.out.println(" 5.Fold");
                P1choice = scanner.next();

                switch (P1choice) {
                    case "1" -> {
                        if (player1.chip >= 100) {
                            player1.Bet(player2,stages);
                            chipPool+=player1.pay;
                            break player1;
                        } else {
                            System.out.println("筹码不足,无法Bet,请按提示重新输入");
                        }
                    }
                    case "2" -> {
                        if (player1.pay!=player2.pay&&(player1.chip - (player2.pay - player1.pay) >= 0)) {
                            player1.Call(player2, stages);
                            chipPool += player1.pay - player1.lastpay;
                            break player1;
                        } else {
                            System.out.println("请按提示重新输入");
                        }
                    }
                    case "3" -> {
                        if (player1.chip - (2 * player2.pay - player1.pay) > 0) {
                            player1.Raise(player2, stages);
                            chipPool += player1.pay - player1.lastpay;
                            break player1;
                        } else {
                            System.out.println("筹码不足,无法Raise,请按提示重新输入");
                        }

                    }
                    case "4" -> {
                        if (player1.pay == player2.pay) {
                            player1.Check(player2, stages);
                            break flop;
                        } else {
                            System.out.println("输入错误,请根据提示重新输入");
                        }
                    }
                    case "5" -> {
                        player1.Fold(player2, stages);
                        player2.chip += chipPool;       //player1认输,把筹码池的筹码都给player2
                        rounds++;
                        break flop;
                    }
                    default -> {
                        System.out.println("输入错误,请根据提示重新输入");
                    }
                }

            }


        }

        stages++;
    }

    /*4.river*/
    public static void river(playerAI player1, playerAI player2, Heguan heguan) {
        //player2先开始
        //player2做选择Bet,Call,Raise,Fold
        player1.pay =0;
        player2.pay =0;
        String P1choice = "";
        String P2choice = "";

        flop:
        while (true){
            String warning = "";

            player2:
            while (true){
                System.out.print("请player2选择:");
                if(player2.chip>=100&&(0==player2.pay)){
                    System.out.print("1.Bet");
                }else{
                    if(player1.pay!=player2.pay&&(player2.chip-(player1.pay-player2.pay)>=0)){//仅在player1raise时可以call
                        System.out.print(" 2.Call ");
                    }
                    if (player2.chip - (2 * player1.pay - player2.pay) > 0 ) {
                        System.out.print(" 3.Raise ");
                    }
                }
                if (player1.pay == player2.pay) {
                    System.out.print("4.Check");
                }
                System.out.println("5.Fold");
                //P2choice = scanner.next();
                P2choice = player2.getRiver_choice(P1choice, heguan.FiledCard,warning);
                System.out.println(P2choice);
                switch (P2choice) {
                    case "1" -> {
                        if (player2.chip>=100&&player2.pay==0) {
                            player2.Bet(player1,stages);
                            chipPool+=player2.pay;
                            break player2;
                        } else {
                            if(player2.chip<100){
                                warning = "筹码不足,无法Bet";
                            }
                            System.out.println(warning+"请按提示重新输入");
                        }
                    }
                    case "2" -> {
                        if (player1.pay!=player2.pay&&(player2.chip - (player1.pay - player2.pay) >= 0)) {
                            player2.Call(player1, stages);
                            chipPool += player2.pay - player2.lastpay;
                            break player2;
                        } else {
                            if(player2.chip - (player1.pay - player2.pay) < 0){
                                warning = "筹码不足,无法Call";
                            }
                            System.out.println(warning+"请按提示重新输入");
                        }
                    }
                    case "3" -> {
                        if ((player2.pay!=0)&&player2.chip - (2 * player1.pay - player2.pay) >= 0) {
                            player2.Raise(player1, stages);
                            chipPool += player2.pay - player2.lastpay;
                            break player2;
                        } else {
                            if(player2.chip - (2 * player1.pay - player2.pay) < 0){
                                warning = "筹码不足,无法Raise";
                            }
                            System.out.println(warning+"请按提示重新输入");
                        }

                    }
                    case "4" -> {
                        if (player1.pay == player2.pay) {
                            player2.Check(player1, stages);
                            break player2;
                        } else {
                            System.out.println("无法Check");
                        }
                    }
                    case "5" -> {
                        player2.Fold(player1, stages);
                        player1.chip += chipPool;       //player2认输,把筹码池的筹码都给player21
                        rounds++;
                        break flop;
                    }
                    default -> {
                        System.out.println("输入错误,请根据提示重新输入");
                    }
                }

            }

            player1:
            while (true){
                System.out.print("请player1选择:");
                if(0==player2.pay&&player1.chip>=100){
                    System.out.print("1.Bet");
                }else{
                    if(player1.pay!=player2.pay&&(player1.chip-(player2.pay-player1.pay)>=0)){//仅在player2raise时可以call
                        System.out.print(" 2.Call ");
                    }
                    if (player1.chip - (2 * player2.pay - player1.pay) > 0 ) {
                        System.out.print(" 3.Raise ");
                    }

                }

                if (player1.pay == player2.pay) {
                    System.out.print(" 4.Check");
                }
                System.out.println(" 5.Fold");
                P1choice = scanner.next();

                switch (P1choice) {
                    case "1" -> {
                        if (player1.chip >= 100) {
                            player1.Bet(player2,stages);
                            chipPool+=player1.pay;
                            break player1;
                        } else {
                            System.out.println("筹码不足,无法Bet,请按提示重新输入");
                        }
                    }
                    case "2" -> {
                        if (player1.pay!=player2.pay&&(player1.chip - (player2.pay - player1.pay) >= 0)) {
                            player1.Call(player2, stages);
                            chipPool += player1.pay - player1.lastpay;
                            break player1;
                        } else {
                            System.out.println("请按提示重新输入");
                        }
                    }
                    case "3" -> {
                        if (player1.chip - (2 * player2.pay - player1.pay) > 0) {
                            player1.Raise(player2, stages);
                            chipPool += player1.pay - player1.lastpay;
                            break player1;
                        } else {
                            System.out.println("筹码不足,无法Raise,请按提示重新输入");
                        }

                    }
                    case "4" -> {
                        if (player1.pay == player2.pay) {
                            player1.Check(player2, stages);
                            break flop;
                        } else {
                            System.out.println("输入错误,请根据提示重新输入");
                        }
                    }
                    case "5" -> {
                        player1.Fold(player2, stages);
                        player2.chip += chipPool;       //player1认输,把筹码池的筹码都给player2
                        rounds++;
                        break flop;
                    }
                    default -> {
                        System.out.println("输入错误,请根据提示重新输入");
                    }
                }

            }


        }

        stages++;
    }

    /*5.PK*/
    public static void PK(playerAI player1, playerAI player2, Heguan heguan) {

        //判断最大牌型的算法放这
        //比较player1和player2的最大牌型,谁的最大牌型更大,谁赢
        int player1_weight = judge67Cards2(player1.handCard, heguan.FiledCard);
        int player2_weight = judge67Cards2(player2.handCard, heguan.FiledCard);

        System.out.println("player1_weight:"+player1_weight);

        System.out.println("player2_weight:"+player2_weight);

        if (player1_weight > player2_weight) {
            System.out.println("本局player1胜");
            player1.chip += chipPool;
        } else if (player1_weight < player2_weight) {
            System.out.println("本局player2胜");
            player2.chip += chipPool;
        } else {
            System.out.println("平局");
            player1.chip += chipPool / 2;
            player2.chip += chipPool / 2;
        }


        stages++;
    }

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        playerAI player1 = new playerAI();
        playerAI player2 = new playerAI();
        Heguan heguan = new Heguan();

        player1.Blind = 1;
        player2.Blind = 2;


        boolean start = true;

        while (start) {
            System.out.printf("----------------------------第%d局--------------------------\n", rounds);
            chipPool = 0;
            stages = 1;
            //下盲注
            player1.pay = 50;
            player1.chip = player1.chip - player1.pay;
            player2.pay = 100;
            player2.chip = player2.chip - player2.pay;
            chipPool = player1.pay + player2.pay;

            System.out.printf("筹码池: %d\n", chipPool);
            System.out.println("player1剩余筹码:" + player1.chip);
            System.out.println("player2剩余筹码:" + player2.chip);
            fapai(player1, player2, heguan);
//            System.out.println("输入stages");
//            stages = scanner.nextInt();//手动输入stages

//            switch (stages) {
//                case 1 -> {
            System.out.println("*****第" + stages + "轮:preflop*****");
            System.out.println("场牌:             ");
            System.out.println("player1的手牌:" + player1.handCard[0].printCard() + player1.handCard[1].printCard());
            System.out.println("player2的手牌:" + player2.handCard[0].printCard() + player2.handCard[1].printCard());
            /**/
            preflop(player1, player2);
            /**/
            //依次判断是否有人Fold,是否需要直接结束本局游戏
            if (player1.isFold) {
                System.out.println("p1Fold");
                player1.isFold = false;
                continue;

            } else {
                if (player2.isFold) {
                    System.out.println("p2Fold");
                    player2.isFold = false;
                    continue;
                }
            }


            //显示对局情况
            System.out.println("*****player1下注:" + player1.pay);
            System.out.println("*****player2下注:" + player2.pay);
            System.out.println("*****当前筹码池:" + chipPool);
            System.out.println("第" + stages + "轮:flop");
            System.out.println("场牌:" + heguan.nowFiledCard(stages));
            /**/
            flop(player1, player2, heguan);
            /**/
            //依次判断是否有人Fold,是否需要直接结束本局游戏
            if (player2.isFold) {

                player2.isFold = false;
                continue;
            } else {
                if (player1.isFold) {

                    player1.isFold = false;
                    continue;
                }

            }

            //显示对局情况
            System.out.println("*****player1下注:" + player1.pay);
            System.out.println("*****player2下注:" + player2.pay);
            System.out.println("*****当前筹码池:" + chipPool);


//                }
//                case 3 -> {
            System.out.println("第" + stages + "轮:turn");
            System.out.println("场牌:" + heguan.nowFiledCard(stages));
            turn(player1, player2, heguan);

            //依次判断是否有人Fold,是否需要直接结束本局游戏
            if (player2.isFold) {

                player2.isFold = false;
                continue;
            } else {
                if (player1.isFold) {

                    player1.isFold = false;
                    continue;
                }
            }


            //显示对局情况
            System.out.println("*****player1下注:" + player1.pay);
            System.out.println("*****player2下注:" + player2.pay);
            System.out.println("*****当前筹码池:" + chipPool);
//                }
//                case 4 -> {
            System.out.println("第" + stages + "轮:river");
            System.out.println("场牌:" + heguan.nowFiledCard(stages));
            /**/
            river(player1, player2, heguan);
            /**/

            //依次判断是否有人Fold,是否需要直接结束本局游戏
            if (player2.isFold) {
                player2.isFold = false;
                continue;
            } else {
                if (player1.isFold) {

                    player1.isFold = false;
                    continue;
                }

            }

            //显示对局情况
            System.out.println("*****player1下注:" + player1.pay);
            System.out.println("*****player2下注:" + player2.pay);
            System.out.println("*****当前筹码池:" + chipPool);

//                }
//                case 5 -> {
            System.out.println("第" + stages + "轮:比牌");
            System.out.println("player1的手牌:" + player1.handCard[0].printCard() + player1.handCard[1].printCard());
            System.out.println("player2的手牌:" + player2.handCard[0].printCard() + player2.handCard[1].printCard());
            System.out.println("场牌:" + heguan.nowFiledCard(stages));
            /**/
            PK(player1, player2, heguan);
            /**/
//
//                }


            rounds++;//本局游戏结束
            System.out.println("本局游戏结束");

            if (player1.chip <= 0 || player2.chip <= 0) {
                System.out.println("游戏结束,胜利者是:player" + (player1.chip > player2.chip ? 1 : 2));
                start = false;
            }

        }

        //break;
    }


}