package org.example.gamedemo;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Character extends Pane {
    protected static final int CELL_SIZE = 40;
    private ImageView gif ;
    protected int currentRow = 0;
    protected int currentColumn = 0;
    private boolean reflected = false;

    public int getCurrentColumn() {
        return currentColumn;
    }

    public int getCurrentRow() {
        return currentRow;
    }


    protected void setPosition(){
        gif.setX(currentColumn*CELL_SIZE -5);
        gif.setY(currentRow*CELL_SIZE);
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
