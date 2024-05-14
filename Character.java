package org.example.gamedemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Character extends Pane {
    protected static final int CELL_SIZE = 40;
    private ImageView gif ;
    protected int currentRow = 0;
    protected int currentColumn = 0;
    protected int nextRow = 0;
    protected int nextColumn = 0;
    private boolean reflected = false;
    private Timeline moverTl;
    private double dx;
    private double dy;
    private Timeline positionTl;
    int counter = 0;
    protected Direction direction;

    public Character(){
        setMoverTl();
        setPositionTl();
    }

    private void setMoverTl(){
        // moverTl  Changes Character Gif Position Every 10ms by 2 Pixels For The Cycle
        // So It Should be Played For 20 Times to Reach 40 Pixels(CELL_WIDTH) [Changes currentRow - currentColumn]
        moverTl =  new Timeline(new KeyFrame(Duration.millis(10), e-> move(dx,dy)));
        moverTl.setCycleCount(CELL_SIZE/2);
        moverTl.setOnFinished(e->{
            counter =0;
        });
    }
    private void move(double dx , double dy){
        gif.setY(gif.getY() + dy * 2);
        gif.setX(gif.getX() + dx * 2);
        counter++;
        // Update Index After 13 Cycles
        if(counter == 13){
            currentRow = nextRow;
            currentColumn = nextColumn;
        }
    }
    private void setPositionTl(){
        // positionTl Changes Character Gif Coordinates Through a Certain Amount of Time (190ms)
        // [Doesn't Change currentRow - currentColumn]
        positionTl = new Timeline(new KeyFrame(Duration.millis(250), e->{
            gif.setX(currentColumn*CELL_SIZE +5);
            gif.setY(currentRow*CELL_SIZE);}
        ));
        positionTl.setCycleCount(1);
    }
    protected void setPosition(int row,int col){
        // Difference in Coordinates [Index]
        dx = col-currentColumn ;
        dy = row -currentRow ;

        // Destination
        nextColumn = col;
        nextRow = row;

        moverTl.play();
    }
    protected void setPosition(){positionTl.play();}

    /*-------------Gif Orientation Methods---------------*/
    public void reflectVerticallyToLeft() {
        // Reset to initial angel
        gif.setRotate(0);
        // Flip the pacman to the left
        gif.setScaleX(-1);
        reflected = true;
    }
    public void rotateToBottom() {
        if(reflected){
            gif.setScaleX(1);
        }
        // Flip the Pacman image horizontally to the bottom
        gif.setRotate(90);
    }
    public void reflectVerticallyToRight() {
        // Flip the Pacman image vertically to the right
        if(reflected){
            gif.setScaleX(1);
        }
        // Reset to initial angel
        gif.setRotate(0);
    }
    public void rotateToTop() {
        if(reflected){
            gif.setScaleX(1);
        }
        // Flip the Pacman image horizontally to the top
        gif.setRotate(270);
    }
    /*--------------------------------------------------*/


    /*-----------------Setters / Getters-----------------*/
    public int getCurrentColumn() {return currentColumn;}
    public int getCurrentRow() {return currentRow;}
    public ImageView getGif(){return gif;}
    public Direction getDirection() {return direction;}
    public void setGif(ImageView gif) {this.gif = gif;}
    public void setDirection(Direction direction) {this.direction = direction;}
    /*-------------------------------------------------*/
}
