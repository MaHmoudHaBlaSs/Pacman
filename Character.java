package org.example.gamedemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Character extends Pane {
    protected static final int CELL_SIZE = 40;
    protected Direction direction;
    private ImageView gif ;
    protected int currentRow = 0;
    protected int currentColumn = 0;
    protected int nextRow = 0;
    protected int nextColumn = 0;
    private int counter = 0;
    private boolean reflected = false;
    private Timeline moverTl;

    private double dx;
    private double dy;


    public Character(){
        setMoverTl();
    }

    private void setMoverTl(){
        // moverTl  Changes Character Gif Position Every 10ms by 2 Pixels For The Cycle
        // So It Should be Played For 20 Times to Reach 40 Pixels(CELL_WIDTH) [Changes currentRow - currentColumn]
        moverTl =  new Timeline(new KeyFrame(Duration.millis(10.1), e-> move()));
        moverTl.setCycleCount(CELL_SIZE/2);
        moverTl.setOnFinished(e->{
            counter =0;
        });
    }
    private void move(){
        gif.setY(gif.getY() + dy * 2);
        gif.setX(gif.getX() + dx * 2);
        counter++;
        // Update Index After 13 Cycles
        if(counter == 13){
            currentRow = nextRow;
            currentColumn = nextColumn;
        }
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

    /*-----------------Setters / Getters-----------------*/
    public int getCurrentColumn() {return currentColumn;}
    public int getCurrentRow() {return currentRow;}
    public ImageView getGif(){return gif;}
    public void setGif(ImageView gif) {this.gif = gif;}
    public void setDirection(Direction direction) {this.direction = direction;}
}
