package UI;

import Card.card;
import Main.Heguan;
import Main.main;
import Player.player;
import Player.playerAI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import static Card.cardSet.judge67Cards2;

public class pokerJFrame extends JFrame implements MouseListener {
    static int stages = 1;
    static int rounds = 1;
    static int chipPool = 0;
    //跳出循环条件
    static boolean breakFlag=false;
    static boolean p1Flop=false;
    static boolean p2Flop=false;

    Thread t1=new Thread();

    //两名玩家以及荷官
    playerAI p1 = new playerAI();
    playerAI p2 = new playerAI();
    Heguan h = new Heguan();


    //两人的选择
    static String P1choice = "";
    static String P2choice = "6";

    String path = "poker/";
    //每局游戏的流程
    String[] liuCheng = "preflop flop turn river PK".split(" ");

    //显示AI的选择
    String[] player2Act="Bet Call Raise Check Fold 无".split(" ");

    //设置player1的按钮
    static JButton playerBtn1_1 = new JButton("call");//玩家一跟注
    static JButton playerBtn1_2 = new JButton("raise");//玩家一加注
    static JButton playerBtn1_3 = new JButton("fold");//玩家一弃牌
    static JButton playerBtn1_4 = new JButton("bet");//玩家一Bet
    static JButton playerBtn1_5 = new JButton("check");//玩家check
    //设置player2的按钮
    static JButton playerBtn2_1 = new JButton("call");//玩家一跟注
    static JButton playerBtn2_2 = new JButton("raise");//玩家一加注
    static JButton playerBtn2_3 = new JButton("fold");//玩家一弃牌
    static JButton playerBtn2_4 = new JButton("bet");//玩家二bet
    static JButton playerBtn2_5 = new JButton("check");//玩家check

    //筹码
    JLabel p1chipBtn = new JLabel("player1筹码：" + p1.chip);
    JLabel p2chipBtn = new JLabel("player2筹码：" + p2.chip);
    JLabel p1payBtn = new JLabel("player1下注:" + p1.pay);
    JLabel p2payBtn = new JLabel("player2下注:" + p2.pay);


    //设置奖池
    JLabel chipPoolBtn = new JLabel("奖池:" + chipPool);//奖池

    //设置局数
    JLabel roundsBtn = new JLabel("第 " + rounds + " 局");

    //设置进行到preflop flop turn river PK 的流程
    JLabel liuChengBtn = new JLabel("现在是: " + liuCheng[stages - 1]);

    //显示player2的操作
    JLabel player2ActBtn=new JLabel("player2选择  "+player2Act[Integer.parseInt(P2choice)-1]);

    //公选牌
    //heguan牌默认是背面
    JLabel btn31 = new JLabel();
    JLabel btn32 = new JLabel();
    JLabel btn33 = new JLabel();
    JLabel btn34 = new JLabel();
    JLabel btn35 = new JLabel();

    //玩家牌
    JLabel p1handCard1=new JLabel();
    JLabel p1handCard2=new JLabel();
    JLabel p2handCard1=new JLabel();
    JLabel p2handCard2=new JLabel();

    //初始化界面
    public void initJFrame() {
        this.setSize(960, 540);
        this.setTitle("DeZhouPoker");
        //设置游戏界面置顶
        this.setAlwaysOnTop(true);
        //设置游戏界面居中
        this.setLocationRelativeTo(null);
        //设置游戏界面退出
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //将默认设置在中间更改,取消默认居中设置，只有更改了，才能按照xy轴的方式设置位置
        this.setLayout(null);
        Color mycolor = new Color(0, 102, 80);//背景颜色
        this.setBackground(mycolor);
        this.getContentPane().setBackground(mycolor);
    }

    //设置按钮
    public void initJLabel() {
        //设置位置
        //按钮
        playerBtn1_1.setBounds(80, 200, 70, 20);
        playerBtn1_2.setBounds(0, 230, 70, 20);
        playerBtn1_3.setBounds(80, 230, 70, 20);
        playerBtn1_4.setBounds(0, 200, 70, 20);
        playerBtn1_5.setBounds(0, 260, 70, 20);
        playerBtn2_1.setBounds(800, 200, 70, 20);
        playerBtn2_2.setBounds(880, 230, 70, 20);
        playerBtn2_3.setBounds(800, 230, 70, 20);
        playerBtn2_4.setBounds(880, 200, 70, 20);
        playerBtn2_5.setBounds(880, 260, 70, 20);


        //添加鼠标监听
        playerBtn1_1.addMouseListener(this);
        playerBtn1_2.addMouseListener(this);
        playerBtn1_3.addMouseListener(this);
        playerBtn1_4.addMouseListener(this);
        playerBtn1_5.addMouseListener(this);
        playerBtn2_1.addMouseListener(this);
        playerBtn2_2.addMouseListener(this);
        playerBtn2_3.addMouseListener(this);
        playerBtn2_4.addMouseListener(this);
        playerBtn2_5.addMouseListener(this);

        //添加组件
        this.getContentPane().add(playerBtn1_1);
        this.getContentPane().add(playerBtn1_2);
        this.getContentPane().add(playerBtn1_3);
        this.getContentPane().add(playerBtn1_5);

        this.getContentPane().add(playerBtn2_1);
        this.getContentPane().add(playerBtn2_2);
        this.getContentPane().add(playerBtn2_3);
        this.getContentPane().add(playerBtn2_5);

        //默认按钮不可见
        playerBtn1_1.setVisible(false);
        playerBtn1_2.setVisible(false);
        playerBtn1_3.setVisible(false);
        playerBtn1_4.setVisible(false);
        playerBtn1_5.setVisible(false);
        playerBtn2_1.setVisible(false);
        playerBtn2_2.setVisible(false);
        playerBtn2_3.setVisible(false);
        playerBtn2_4.setVisible(false);
        playerBtn2_5.setVisible(false);
        //只有第二轮后才会有bet按钮
        if (stages >= 2) {
            this.getContentPane().add(playerBtn1_4);
            this.getContentPane().add(playerBtn2_4);
        }
    }

    //设置奖池，筹码，局数位置
    private void initChip() {
        //设置筹码
        p1chipBtn.setBounds(175, 230, 150, 20);
        p2chipBtn.setBounds(675, 230, 150, 20);
        p1payBtn.setBounds(0, 100, 150, 20);
        p2payBtn.setBounds(830, 100, 150, 20);

        //设置奖池
        chipPoolBtn.setBounds(450, 230, 460, 200);

        //设置局数
        roundsBtn.setBounds(300, 0, 50, 100);

        //设置流程
        liuChengBtn.setBounds(450, 0, 100, 100);

        //设置p2的操作
        player2ActBtn.setBounds(600,0,200,100);
        //添加组件
        this.getContentPane().add(p1chipBtn);
        this.getContentPane().add(p2chipBtn);
        this.getContentPane().add(p1payBtn);
        this.getContentPane().add(p2payBtn);
        this.getContentPane().add(chipPoolBtn);
        this.getContentPane().add(roundsBtn);
        this.getContentPane().add(liuChengBtn);
        this.getContentPane().add(player2ActBtn);
    }

    //设置公选牌
    public void setHeguanCard() {
        //heguan的牌
        switch (stages) {
            case 1 -> {
                //五张公共牌图片
                btn31.setIcon(new ImageIcon(path + "rear.png"));
                btn32.setIcon(new ImageIcon(path + "rear.png"));
                btn33.setIcon(new ImageIcon(path + "rear.png"));
                btn34.setIcon(new ImageIcon(path + "rear.png"));
                btn35.setIcon(new ImageIcon(path + "rear.png"));
                btn31.setBounds(180, 100, 71, 96);
                btn32.setBounds(305, 100, 71, 96);
                btn33.setBounds(430, 100, 71, 96);
                btn34.setBounds(555, 100, 71, 96);
                btn35.setBounds(680, 100, 71, 96);
                this.getContentPane().add(btn31);
                this.getContentPane().add(btn32);
                this.getContentPane().add(btn33);
                this.getContentPane().add(btn34);
                this.getContentPane().add(btn35);
            }
            case 2 -> {
                btn31.setIcon((new ImageIcon(path + h.FiledCard[0].color + "-" + h.FiledCard[0].number + ".png")));
                btn32.setIcon((new ImageIcon(path + h.FiledCard[1].color + "-" + h.FiledCard[1].number + ".png")));
                btn33.setIcon((new ImageIcon(path + h.FiledCard[2].color + "-" + h.FiledCard[2].number + ".png")));
                btn31.setBounds(180, 100, 71, 96);
                btn32.setBounds(305, 100, 71, 96);
                btn33.setBounds(430, 100, 71, 96);
                btn34.setBounds(555, 100, 71, 96);
                btn35.setBounds(680, 100, 71, 96);
                this.getContentPane().add(btn31);
                this.getContentPane().add(btn32);
                this.getContentPane().add(btn33);
                this.getContentPane().add(btn34);
                this.getContentPane().add(btn35);
            }
            case 3 -> {
                btn31.setIcon((new ImageIcon(path + h.FiledCard[0].color + "-" + h.FiledCard[0].number + ".png")));
                btn32.setIcon((new ImageIcon(path + h.FiledCard[1].color + "-" + h.FiledCard[1].number + ".png")));
                btn33.setIcon((new ImageIcon(path + h.FiledCard[2].color + "-" + h.FiledCard[2].number + ".png")));
                btn34.setIcon((new ImageIcon(path + h.FiledCard[3].color + "-" + h.FiledCard[3].number + ".png")));
                btn31.setBounds(180, 100, 71, 96);
                btn32.setBounds(305, 100, 71, 96);
                btn33.setBounds(430, 100, 71, 96);
                btn34.setBounds(555, 100, 71, 96);
                btn35.setBounds(680, 100, 71, 96);
                this.getContentPane().add(btn31);
                this.getContentPane().add(btn32);
                this.getContentPane().add(btn33);
                this.getContentPane().add(btn34);
                this.getContentPane().add(btn35);
            }
            default -> {
                btn31.setIcon((new ImageIcon(path + h.FiledCard[0].color + "-" + h.FiledCard[0].number + ".png")));
                btn32.setIcon((new ImageIcon(path + h.FiledCard[1].color + "-" + h.FiledCard[1].number + ".png")));
                btn33.setIcon((new ImageIcon(path + h.FiledCard[2].color + "-" + h.FiledCard[2].number + ".png")));
                btn34.setIcon((new ImageIcon(path + h.FiledCard[3].color + "-" + h.FiledCard[3].number + ".png")));
                btn35.setIcon((new ImageIcon(path + h.FiledCard[4].color + "-" + h.FiledCard[4].number + ".png")));
                btn31.setBounds(180, 100, 71, 96);
                btn32.setBounds(305, 100, 71, 96);
                btn33.setBounds(430, 100, 71, 96);
                btn34.setBounds(555, 100, 71, 96);
                btn35.setBounds(680, 100, 71, 96);
                this.getContentPane().add(btn31);
                this.getContentPane().add(btn32);
                this.getContentPane().add(btn33);
                this.getContentPane().add(btn34);
                this.getContentPane().add(btn35);
            }

        }

    }
    //设置玩家手牌
    public void setPlayerCard() {
        //player1
        p1handCard1 = new JLabel(new ImageIcon(path + p1.handCard[0].color + "-" + p1.handCard[0].number + ".png"));
         p1handCard2 = new JLabel(new ImageIcon(path + p1.handCard[1].color + "-" + p1.handCard[1].number + ".png"));
        p1handCard1.setBounds(0, 300, 71, 96);
        p1handCard2.setBounds(80, 300, 71, 96);
        this.getContentPane().add(p1handCard1);
        this.getContentPane().add(p1handCard2);
        //player2
        if (stages==5) {
            p2handCard1 = new JLabel(new ImageIcon(path + p2.handCard[0].color + "-" + p2.handCard[0].number + ".png"));
            p2handCard2 = new JLabel(new ImageIcon(path + p2.handCard[1].color + "-" + p2.handCard[1].number + ".png"));
        }
        else
        {
            p2handCard1 = new JLabel(new ImageIcon(path + "rear.png"));
            p2handCard2 = new JLabel(new ImageIcon(path + "rear.png"));
        }
        p2handCard1.setBounds(795, 300, 71, 96);
        p2handCard2.setBounds(875, 300, 71, 96);
        this.getContentPane().add(p2handCard1);
        this.getContentPane().add(p2handCard2);
    }

    //更新所有显示的
    public void changeShow() {
        //清除五张公共牌
        this.getContentPane().remove(btn31);
        this.getContentPane().remove(btn32);
        this.getContentPane().remove(btn33);
        this.getContentPane().remove(btn34);
        this.getContentPane().remove(btn35);
        //
        this.getContentPane().remove(p1handCard1);
        this.getContentPane().remove(p1handCard2);
        this.getContentPane().remove(p2handCard1);
        this.getContentPane().remove(p2handCard2);
        //清除奖池
        this.getContentPane().remove(chipPoolBtn);
        //清除局数
        this.getContentPane().remove(roundsBtn);
        //清除筹码
        this.getContentPane().remove(p1chipBtn);
        this.getContentPane().remove(p2chipBtn);
        this.getContentPane().remove(p1payBtn);
        this.getContentPane().remove(p2payBtn);

        //刷新删除的数据（将新的数据加上）
        initChip();
        //更新公选牌
        setHeguanCard();
        //更新玩家牌
        setPlayerCard();
        //更新奖池
        chipPoolBtn.setText("奖池:" + chipPool);
        //更新局数
        roundsBtn.setText("第 " + rounds + " 局");
        //更新流程
        liuChengBtn.setText("现在是: " + liuCheng[stages - 1]);
        //更新筹码
        p1chipBtn.setText("player1筹码：" + p1.chip);
        p2chipBtn.setText("player2筹码：" + p2.chip);
        p1payBtn.setText("player1下注：" + p1.pay);
        p2payBtn.setText("player2下注：" + p2.pay);
        //更新p2操作
        player2ActBtn.setText("player2选择：  "+player2Act[Integer.parseInt(P2choice)-1]);

        //刷新一下界面
        this.getContentPane().repaint();

    }

    public pokerJFrame() {
        p1.Blind = 1;
        p2.Blind = 2;
        P2choice="6";

        initJFrame();
        initJLabel();
        loop:
        while (true) {
            breakFlag=false;
            stages = 1;
            //下盲注
            p1.pay = 50;
            p1.chip = p1.chip - p1.pay;
            p2.pay = 100;
            p2.chip = p2.chip - p2.pay;
            chipPool = p1.pay + p2.pay;
            main.fapai(p1, p2, h);
            changeShow();
           // System.out.println("------------"+stages+"----------");
            this.setVisible(true);
            preflop(p1,p2);
            //System.out.println("breakFlag"+breakFlag);
            if (p1.isFold) {
                p1.isFold = false;
                continue;
            }
            changeShow();
            flop(p1,p2,h);
            if (p1.isFold) {
                p1.isFold = false;
                continue;
            }
            changeShow();
            turn(p1,p2,h);
            if (p1.isFold) {
                p1.isFold = false;
                continue;
            }
            changeShow();
            river(p1,p2,h);
            if (p1.isFold) {
                p1.isFold = false;
                continue;
            }
           changeShow();
           //本局结束
           PK(p1,p2,h);
           rounds++;
           stages=1;
           changeShow();
           //判断整个游戏结束
            if (p1.chip <= 0 || p2.chip <= 0) {
                System.out.println("游戏结束,胜利者是:player" + (p1.chip > p2.chip ? 1 : 2));
                if(p1.chip> p2.chip)
                    showDg("player1 获胜","本轮游戏结果");
                else
                    showDg("player2 获胜","本轮游戏结果");
               break loop;
            }
        }


    }



    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == playerBtn1_1) {
            switch (stages) {
                case 1->{
                    if (p1.chip - (p2.pay - p1.pay) >= 0) {
                        p1.Call(p2, stages);
                        chipPool += p1.pay - p1.lastpay;
                        p1Flop=true;
                    } else {
                       showDg("筹码不足,无法Call,请按提示重新输入","提示");
                        System.out.println("筹码不足,无法Call,请按提示重新输入");
                    }
                }
                default->{
                    if (p1.pay!=p2.pay&&(p1.chip - (p2.pay - p1.pay) >= 0)) {
                    p1.Call(p2, stages);
                    chipPool += p1.pay - p1.lastpay;
                    p1Flop=true;
                    p2Flop=false;
                } else {
                        showDg("筹码不足,无法Call,请按提示重新输入","提示");
                        System.out.println("筹码不足,无法Call,请按提示重新输入");
                       }
                }
            }
            P1choice="2";
            System.out.println("p1call");
            changeShow();
        } else if (obj == playerBtn1_2) {
            switch (stages) {
                case 1-> {
                    if (p1.chip - (2 * p2.pay - p1.pay) > 0) {
                        p1.Raise(p2, stages);
                        chipPool += p1.pay - p1.lastpay;
                        p1Flop=true;
                    } else {
                        showDg("筹码不足,无法Raise,请按提示重新输入","提示");
                        System.out.println("筹码不足,无法Raise,请按提示重新输入");
                    }
                }
                default-> {
                    if (p1.chip - (2 * p2.pay - p1.pay) > 0) {
                        p1.Raise(p2, stages);
                        chipPool += p1.pay - p1.lastpay;
                        p1Flop=true;
                        p2Flop=false;
                    } else {
                        showDg("筹码不足,无法Raise,请按提示重新输入","提示");
                        System.out.println("筹码不足,无法Raise,请按提示重新输入");
                    }
                }
            }
            P1choice="3";
            System.out.println("p1raise");
            changeShow();
        } else if (obj == playerBtn1_3) {
               p1.Fold(p2, stages);
               p2.chip += chipPool;       //player1认输,把筹码池的筹码都给player2
               rounds++;
               breakFlag=true;
               stages=1;
               showDg("player2获胜","本轮游戏结果");
            P1choice="5";
            System.out.println("p1fold");
            changeShow();
        } else if (obj == playerBtn1_4) {
               if (p1.chip >= 100) {
                   p1.Bet(p2,stages);
                   chipPool+=p1.pay;
                   p1Flop=true;
                   p2Flop=false;
               } else {
                   showDg("筹码不足,无法Bet,请按提示重新输入","提示");
               }
            P1choice="1";
            System.out.println("p1bet");
            changeShow();
        } else if (obj == playerBtn1_5) {
            switch (stages) {
                case 1 -> {
                    if (p1.pay == p2.pay) {
                        p1.Check(p2, stages);
                        p1Flop = true;
                        //最后应该是在player2那里的check结束
                    } else {
                        showDg("筹码不足,无法check,请按提示重新输入","提示");
                        System.out.println("筹码不足,无法check,请按提示重新输入");
                    }
                }
                default -> {
                    if (p1.pay == p2.pay) {
                        p1.Check(p2, stages);
                        breakFlag = true;
                    } else {
                        //弹窗
                        showDg("筹码不足,无法check,请按提示重新输入","提示");
                        System.out.println("无法check");
                    }
                }
            }
            P1choice="4";
            System.out.println("p1check");
            changeShow();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void preflop(playerAI player1, playerAI player2) {
        preflop:
        //break label表示玩家都执行完操作了,可以结束preflop且能进去flop了
        while (true) {
            player1:
            //break label1表示player1操作完了,该轮到player2了
            do {
                removeP1btn();
                if (player1.pay != player2.pay && (player1.chip - (player2.pay - player1.pay) >= 0)) {
                    playerBtn1_1.setVisible(true);
                }
                if (player1.chip - (2 * player2.pay - player1.pay) > 0) {
                    playerBtn1_2.setVisible(true);
                }
                if (player1.pay == player2.pay) {
                    playerBtn1_5.setVisible(true);
                }
                playerBtn1_3.setVisible(true);

                if(p1Flop){
                    break player1;
                }
                if(breakFlag){
                    break preflop;
                }
                //停1s
                try {
                    t1.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);

            System.out.println("-----------------perflop player1结束---------");
            player2:
            //break label2表示player2操作完了,该准备结束preflop了
            while (true) {
                //p2操作
                P2choice = player2.getPreflop_choice(P1choice, rounds);
                System.out.println("player2选择"+P2choice);
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
                }
                changeShow();
            }
            System.out.println("-------------preflop player2结束----------------");
            p1Flop=false;
            p2Flop=false;
            breakFlag=false;
        }
        stages++;
    }

    public void flop(playerAI player1, playerAI player2, Heguan heguan) {
        //player2先开始
        //player2做选择Bet,Call,Raise,Fold
        player1.pay =0;
        player2.pay =0;
        breakFlag=false;
        p1Flop=false;
        P2choice="6";
        flop:
        while (true){
            String warning = "";
            System.out.println("---------------flop player2开始-------------------");
            player2:
            while (true){
                //p2操作
                P2choice = player2.getFlop_choice(P1choice, heguan.FiledCard,warning);
                System.out.println("player2选择"+P2choice);
                switch (P2choice) {
                    case "1" -> {
                        if (player2.chip>=100&&player2.pay==0) {
                            player2.Bet(player1,stages);
                            chipPool+=player2.pay;
                            System.out.println("p2bet");
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
                            System.out.println("p2call");
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
                            System.out.println("p2raise");
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
                            System.out.println("p2checkflop");
                            break player2;
                        } else {
                            System.out.println("无法Check flop");
                        }
                    }
                    case "5" -> {
                        player2.Fold(player1, stages);
                        player1.chip += chipPool;       //player2认输,把筹码池的筹码都给player21
                        rounds++;
                        break flop;
                    }
                }
             changeShow();
                //停1s
                try {
                    t1.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //更新除牌外的
            changeShow();
           /* //更新奖池
            chipPoolBtn.setText("奖池:" + chipPool);
            //更新局数
            roundsBtn.setText("第 " + rounds + " 局");

            //更新筹码
            p1chipBtn.setText("player1筹码：" + p1.chip);
            p2chipBtn.setText("player2筹码：" + p2.chip);
            p1payBtn.setText("player1下注：" + p1.pay);
            p2payBtn.setText("player2下注：" + p2.pay);

            //更新p2操作
            player2ActBtn.setText("player2选择：  "+player2Act[Integer.parseInt(P2choice)-1]);*/


            System.out.println("--------flop player1开始-----------------");
            player1:
            while (true){
                removeP1btn();
                if(0==player2.pay&&player1.chip>=100){
                    playerBtn1_4.setVisible(true);
                }else{
                    if(player1.pay!=player2.pay&&(player1.chip-(player2.pay-player1.pay)>=0)){//仅在player2raise时可以call
                        playerBtn1_1.setVisible(true);
                    }
                    if (player1.chip - (2 * player2.pay - player1.pay) > 0 ) {
                        playerBtn1_2.setVisible(true);
                    }
                }
                if (player1.pay == player2.pay) {
                   playerBtn1_5.setVisible(true);
                }
                playerBtn1_3.setVisible(true);

                if(p1Flop){
                    break player1;
                }
                if(breakFlag){
                    break flop;
                }
                try {
                    t1.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("--------flop player1结束-----------------");
            p1Flop=false;
            p2Flop=false;
            breakFlag=false;
        }
        stages++;
    }

    public  void turn(playerAI player1, playerAI player2, Heguan heguan) {
        //player2先开始
        //player2做选择Bet,Call,Raise,Fold
        player1.pay =0;
        player2.pay =0;
        P1choice = "";
        P2choice = "6";
        breakFlag=false;
        p1Flop=false;
        flop:
        while (true){
            String warning = "";
            System.out.println("-------turn player2开始----------");
            player2:
            while (true){
                //p2操作
                P2choice = player2.getTurn_choice(P1choice, heguan.FiledCard,warning);
                System.out.println("player2选择"+P2choice);
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
                }
                //停1s
                try {
                    t1.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //更新除牌外的
            changeShow();
 /*           //更新奖池
            chipPoolBtn.setText("奖池:" + chipPool);
            //更新局数
            roundsBtn.setText("第 " + rounds + " 局");

            //更新筹码
            p1chipBtn.setText("player1筹码：" + p1.chip);
            p2chipBtn.setText("player2筹码：" + p2.chip);
            p1payBtn.setText("player1下注：" + p1.pay);
            p2payBtn.setText("player2下注：" + p2.pay);
            //更新p2操作
            player2ActBtn.setText("player2选择：  "+player2Act[Integer.parseInt(P2choice)-1]);*/

            System.out.println("--------turn player1开始-----------------");
            player1:
            while (true){
                removeP1btn();
                if(0==player2.pay&&player1.chip>=100){
                    playerBtn1_4.setVisible(true);
                }else{
                    if(player1.pay!=player2.pay&&(player1.chip-(player2.pay-player1.pay)>=0)){//仅在player2raise时可以call
                        playerBtn1_1.setVisible(true);
                    }
                    if (player1.chip - (2 * player2.pay - player1.pay) > 0 ) {
                        playerBtn1_2.setVisible(true);
                    }

                }

                if (player1.pay == player2.pay) {
                    playerBtn1_5.setVisible(true);
                }
                playerBtn1_3.setVisible(true);

                if(p1Flop){
                    break player1;
                }
                if(breakFlag){
                    break flop;
                }
                try {
                    t1.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("----turn player1结束------");
            p1Flop=false;
            p2Flop=false;
            breakFlag=false;
        }
        stages++;
    }

    public  void river(playerAI player1, playerAI player2, Heguan heguan) {
        //player2先开始
        //player2做选择Bet,Call,Raise,Fold
        player1.pay =0;
        player2.pay =0;
        P1choice = "";
        P2choice = "6";
        breakFlag=false;
        p1Flop=false;
        flop:
        while (true){
            String warning = "";
            System.out.println("-------river player2开始----------");
            player2:
            while (true){
                //p2操作
                P2choice = player2.getRiver_choice(P1choice, heguan.FiledCard,warning);
                System.out.println("player2选择"+P2choice);
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
                }
                try {
                    t1.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            //更新除牌外的
            changeShow();
        /*    //更新奖池
            chipPoolBtn.setText("奖池:" + chipPool);
            //更新局数
            roundsBtn.setText("第 " + rounds + " 局");

            //更新筹码
            p1chipBtn.setText("player1筹码：" + p1.chip);
            p2chipBtn.setText("player2筹码：" + p2.chip);
            p1payBtn.setText("player1下注：" + p1.pay);
            p2payBtn.setText("player2下注：" + p2.pay);
            //更新p2操作
            player2ActBtn.setText("player2选择：  "+player2Act[Integer.parseInt(P2choice)-1]);
*/

            System.out.println("------------river player1开始--------");
            player1:
            while (true){
                removeP1btn();
                if(0==player2.pay&&player1.chip>=100){
                    playerBtn1_4.setVisible(true);
                }else{
                    if(player1.pay!=player2.pay&&(player1.chip-(player2.pay-player1.pay)>=0)){//仅在player2raise时可以call
                        playerBtn1_1.setVisible(true);
                    }
                    if (player1.chip - (2 * player2.pay - player1.pay) > 0 ) {
                        playerBtn1_2.setVisible(true);
                    }

                }
                if (player1.pay == player2.pay) {
                    playerBtn1_5.setVisible(true);
                }
                playerBtn1_3.setVisible(true);

                if(p1Flop){
                    break player1;
                }
                if(breakFlag){
                    break flop;
                }
                try {
                    t1.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("----river player1结束------");
            p1Flop=false;
            p2Flop=false;
            breakFlag=false;
        }
        stages++;
    }
    public  void PK(playerAI player1, playerAI player2, Heguan heguan) {
        //判断最大牌型的算法放这
        //比较player1和player2的最大牌型,谁的最大牌型更大,谁赢
        int player1_weight = judge67Cards2(player1.handCard, heguan.FiledCard);
        int player2_weight = judge67Cards2(player2.handCard, heguan.FiledCard);
        if (player1_weight > player2_weight) {
            //弹窗
            showDg("player1获胜","本轮游戏结果");
            System.out.println("本局player1胜");
            player1.chip += chipPool;
        } else if (player1_weight < player2_weight) {
            showDg("player2获胜","本轮游戏结果");
            player2.chip += chipPool;
        } else {
           showDg("平局","本轮游戏结果");
            player1.chip += chipPool / 2;
            player2.chip += chipPool / 2;
        }

        stages++;
    }

    public void showDg(String str,String title){
        JDialog jDialog=new JDialog();
        jDialog.setTitle(title);
        JTextField jTextField=new JTextField(str);
        jTextField.setBounds(20,0,100,200);
        jDialog.getContentPane().add(jTextField);
        jDialog.setSize(300,150);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        //弹窗不关闭无法操作
        jDialog.setModal(true);
        jDialog.setVisible(true);
    }
    public void removeP1btn(){
        playerBtn1_1.setVisible(false);
        playerBtn1_2.setVisible(false);
        playerBtn1_3.setVisible(false);
        playerBtn1_4.setVisible(false);
        playerBtn1_5.setVisible(false);
    }

}
