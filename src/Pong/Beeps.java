package Pong;

import java.awt.*;


public class Beeps extends Rectangle {


    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;
    String beep="Beep";
    String boop="Boop";

    Beeps(int GAME_WIDTH, int GAME_HEIGHT){
        Beeps.GAME_WIDTH=GAME_WIDTH;
        Beeps.GAME_HEIGHT=GAME_HEIGHT;

    }
    public void draw(Graphics g){

        g.setFont(new Font("Calibre",Font.PLAIN,20));
        if(player1==1){
            g.setColor(Color.white);
            g.drawString(beep,20, 20);
            player1=0;
    }
        if(player2==1){
            g.setColor(Color.white);
            g.drawString(boop,GAME_WIDTH-100, 20);
            player2=0;
        }
}}
