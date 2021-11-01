package Pong;

import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{

    Random random;
    int xVelocity;
    int yVelocity;
    int shape;
    int initialSpeed=20;//todo everytime you collide with a paddle increase by a random tiny bit?
    Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        random=new Random();
        int randomXDirection=random.nextInt(2);
        if(randomXDirection==0)
            randomXDirection--;
        setXDirection(randomXDirection*(initialSpeed/10));

        int randomYDirection=random.nextInt(2);
        if(randomYDirection==0)
            randomYDirection--;
        setYDirection(randomYDirection*(initialSpeed/10));
            }
    public void setXDirection(int randomXDirection){
        xVelocity=randomXDirection;
    }
    public void setYDirection(int randomYDirection){
        yVelocity=randomYDirection;
    }
    public void move(){
        x +=xVelocity;
        y +=yVelocity;
    }
    public void draw(Graphics g){
        if(shape==0) {
            g.setColor(Color.blue);
            g.fillRect(x, y, height, width);
        }if(shape==1){
            g.setColor(Color.red);
            g.fillOval(x, y, height, width);
        }
    }
}
