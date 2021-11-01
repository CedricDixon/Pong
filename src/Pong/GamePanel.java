package Pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable{
    //todo add bleep and bloop text popups when the ball hits a paddle
    //todo maybe make one side rounded and the other rectangular and change the ball when it impacts if possible
    static final int GAME_WIDTH = 1200;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH= 25;
    static final int PADDLE_HEIGHT= 100;
    int bounces=0;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball, ball2;
    Score score;
    Beeps beeps;
    GamePanel(){
        newPaddels();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        beeps= new Beeps(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread=new Thread(this);
        gameThread.start();
    }
    public void newBall(){
        random=new Random();
        ball =new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
        ball2=new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
    }

    public void newPaddels(){
        paddle1=new Paddle(10,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2=new Paddle(GAME_WIDTH-PADDLE_WIDTH-10,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }
    public void paint (Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics=image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);

    }
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        beeps.draw(g);
        if(bounces>8){
            ball2.draw(g);
        }

    }
    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
        if(bounces>8){
            ball2.move();
        }

    }
    public void checkCollision(){
        random=new Random();
        //ball wall bounce
        if(ball.y<=0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y>=GAME_HEIGHT-BALL_DIAMETER){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball2.y<=0){
            ball2.setYDirection(-ball2.yVelocity);
        }
        if(ball2.y>=GAME_HEIGHT-BALL_DIAMETER) {
            ball2.setYDirection(-ball2.yVelocity);
        }
        //bounce ball off paddles
        if(ball.intersects(paddle1)){
            beeps.player1++;
            ball.shape=0;
            bounces++;
            int speedupdown=random.nextInt(2);//decides if speed will increase or decrease
            int speedchange=random.nextInt(5);//dictates how much the speed will change by
            int speedupdowny=random.nextInt(2);//decides if speed will increase or decrease
            int speedchangey=random.nextInt(5);//dictates how much the speed will change by
            ball.xVelocity=Math.abs(ball.xVelocity);
            if(speedupdown==0){
                ball.xVelocity+= speedchange+1;//increase X speed per bounce
            }
            else{
                ball.xVelocity-= speedchange+1;//decreases X speed per bounce
            }
            if (speedupdowny == 0) {
                ball.yVelocity += speedchangey/10 + 1;//increase Y speed per bounce
            } else {
                ball.yVelocity -= speedchangey/10 + 1;//decreases Y speed per bounce
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)){
            beeps.player2++;
            ball.shape=1;
            bounces++;
            int speedupdown=random.nextInt(2);//decides if speed will increase or decrease
            int speedchange=random.nextInt(10);//dictates how much the speed will change by
            int speedupdowny=random.nextInt(2);//decides if speed will increase or decrease
            int speedchangey=random.nextInt(10);//dictates how much the speed will change by
            ball.xVelocity=Math.abs(ball.xVelocity);
            if(speedupdown==0){
                ball.xVelocity+= speedchange+1;//increase X speed per bounce
            }
            else{
                ball.xVelocity-= speedchange+1;//decreases X speed per bounce
            }
            if (speedupdowny == 0) {
                ball.yVelocity += speedchangey/10 + 1;//increase Y speed per bounce
            } else {
                ball.yVelocity -= speedchangey/10 + 1;//decreases Y speed per bounce
            }
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
            if(ball2.intersects(paddle1)) {
                ball2.shape=0;
                beeps.player1++;
                ball2.xVelocity = Math.abs(ball2.xVelocity);
                ball2.setXDirection(ball2.xVelocity);
                ball2.setYDirection(ball2.yVelocity);
            }
            if(ball2.intersects(paddle2)) {
                ball2.shape=1;
                beeps.player2++;
                ball2.xVelocity = Math.abs(ball2.xVelocity);
                ball2.setXDirection(-ball2.xVelocity);
                ball2.setYDirection(ball2.yVelocity);
            }

        //stops paddles from leaving the screen
        if(paddle1.y<=0)
            paddle1.y=0;
        if(paddle1.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle1.y =GAME_HEIGHT-PADDLE_HEIGHT;
        if(paddle2.y<=0)
            paddle2.y=0;
        if(paddle2.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle2.y =GAME_HEIGHT-PADDLE_HEIGHT;
        //give points when scoring and creates new panels and ball
        if(ball.x<=0|| ball2.x<=0){
            score.player2++;
            newPaddels();
            newBall();
            bounces=0;
        }
        if(ball.x>=GAME_WIDTH-BALL_DIAMETER ||ball2.x>=GAME_WIDTH-BALL_DIAMETER){
            score.player1++;
            newPaddels();
            newBall();
            bounces=0;
        }

    }

    public void run(){
        //game loop
        long lastTime =System.nanoTime();
        double amountOfTicks =60;//as in game ticks, not the bugs
        double ns=1000000000/amountOfTicks;
        double delta=0;
        while(true){
            long now=System.nanoTime();
            delta +=(now-lastTime)/ns;
            lastTime=now;
            if(delta>=1){
                move();
                checkCollision();
                repaint();
                delta--;

            }
        }
    }
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);


        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);

        }
    }
}
