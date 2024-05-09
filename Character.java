package com.example.pac_man;

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
    private boolean reflected = false;
    private Timeline mover ;
    private double dx;
    private double dy;
    private Timeline setPositionTm;

    public Character(){
        mover =  new Timeline(new KeyFrame(Duration.millis(10), e-> move(dx,dy)));
        mover.setCycleCount(CELL_SIZE/2);

        setPositionTm = new Timeline(new KeyFrame(Duration.millis(250),e->{
            gif.setX(currentColumn*CELL_SIZE -5);
            gif.setY(currentRow*CELL_SIZE);}
        ));
        setPositionTm.setCycleCount(1);
    }
    public int getCurrentColumn() {
        return currentColumn;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    protected void setPosition(int row,int col){

        dx = col-currentColumn ;
        dy = row -currentRow ;

        currentColumn = col;
        currentRow = row;

        mover.play();


    }
    private void move(double dx , double dy){
        gif.setY(gif.getY() + dy * 2);
        gif.setX(gif.getX() + dx * 2);
    }
    protected void setPosition(){
        setPositionTm.play();
    }

    public void setGif(ImageView gif) {
        this.gif = gif;
    }
    public ImageView getGif(){
        return gif;
    }
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

}
