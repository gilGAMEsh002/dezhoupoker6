package Card;

public class card {
    public int number ;              //2,3,4,5,6,7,8,9,10,11,12,13,14}
    public int color ;    //1为♥,2为♦,3为♠,4为♣

    public boolean exitCard = true;    //牌存在为:true

    public card(int number, int color) {
        this.number = number;
        this.color = color;
    }

    public card(){
        this.number = 0;
        this.color = 0;
    }

    @Override
    public String toString() {
        String value="";
//        switch (color){
//            case 1->value+="♥";
//            case 2->value+="♦";
//            case 3->value+="♠";
//            case 4->value+="♣";
//            default -> value+="";
//        }
//        switch (number){
//            case 11->value+="J";
//            case 12->value+="Q";
//            case 13->value+="K";
//            case 14->value+="A";
//            default -> value+=number;
//        }
//        return " "+value+" ";

        return value+color+number;
    }

    public String printCard(){
        String value="";
        switch (color){
            case 1->value+="♥";
            case 2->value+="♦";
            case 3->value+="♠";
            case 4->value+="♣";
            default -> value+="";
        }
        switch (number){
            case 11->value+="J";
            case 12->value+="Q";
            case 13->value+="K";
            case 14->value+="A";
            default -> value+=number;
        }
        return " "+value+" ";
    }

}