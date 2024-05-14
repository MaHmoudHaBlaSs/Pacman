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
    private boolean reflected = false;
    private Timeline mover ; // Responsible for Smooth Transition for the Gif
    private double dx;
    private double dy;
    private Timeline setPositionTm;

    public Character(){

        // Changes Character Gif Position Every 10ms by 2 Pixels For The Cycle So It Should be Played For 20 Times to Reach 40 Pixels(CELL_WIDTH)
        // [Doesn't Change currentRow - currentColumn]
        mover =  new Timeline(new KeyFrame(Duration.millis(10), e-> {
            gif.setY(gif.getY() + dy * 2);
            gif.setX(gif.getX() + dx * 2);
        }));
        mover.setCycleCount(CELL_SIZE/2); // Playing For 20 Cycles.

        // Changes Character Gif Coordinates Through a Certain Amount of Time (190ms)
        // [Doesn't Change currentRow - currentColumn]
        setPositionTm = new Timeline(new KeyFrame(Duration.millis(190),e->{
            gif.setX(currentColumn*CELL_SIZE -5);
            gif.setY(currentRow*CELL_SIZE);
        }));
        setPositionTm.setCycleCount(1); // Playing Once Per Move (By Invoking 'setPosition' Method).
    }
    public int getCurrentColumn() {return currentColumn;}
    public int getCurrentRow() {return currentRow;}
    public ImageView getGif(){return gif;}
    protected void setPosition(){setPositionTm.play();}
    public void setGif(ImageView gif) {this.gif = gif;}
    protected void setPosition(int row,int col){

        // One of dx Or dy Will Equal to 1 And Another Will Be 0
        dx = col-currentColumn;
        dy = row -currentRow;
        // Update the Position
        currentColumn = col;
        currentRow = row;

        mover.play();
    }

    private void move(double dx , double dy){
        gif.setY(gif.getY() + dy * 2);
        gif.setX(gif.getX() + dx * 2);
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
