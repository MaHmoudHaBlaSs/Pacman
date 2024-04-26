package org.example.gamedemo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character {
    protected static final int CELL_SIZE = 40;
    protected static final int BOARD_WIDTH =760;
    protected static final int BOARD_HEIGHT = 760;
    protected Image image;

    public void setImage(Image image) {
        this.image = image;
    }

    protected ImageView character_gif;
    protected boolean reflected = false;
    protected int i;
    protected int j;
    protected Maze maze ;
    protected MazeView mazeView;
    public Character( MazeView mazeView, Image img){
        this.mazeView = mazeView;
        this.image = img;
        maze = mazeView.getMaze();


        // Create the ImageView , stick it to the maze
        character_gif = new ImageView(img);
        mazeView.getChildren().add(character_gif);

    }
    public void reflectVerticallyToLeft() {
        // Reset to initial angel
        character_gif.setRotate(0);
        // Flip the pacman to the left
        character_gif.setScaleX(-1);
        reflected = true;
    }
    public void rotateToBottom() {
        if(reflected){
            character_gif.setScaleX(1);
        }
        // Flip the Pacman image horizontally to the bottom
        character_gif.setRotate(90);
    }
    public void reflectVerticallyToRight() {
        // Flip the Pacman image vertically to the right
        if(reflected){
            character_gif.setScaleX(1);
        }
        // Reset to initial angel
        character_gif.setRotate(0);
    }
    public void rotateToTop() {
        if(reflected){
            character_gif.setScaleX(1);
        }
        // Flip the Pacman image horizontally to the top
        character_gif.setRotate(270);
    }
    protected void setPosition(){
        character_gif.setX(j*CELL_SIZE -5);
        character_gif.setY(i*CELL_SIZE);
    }
       public void moveRight(){
        if(!maze.isWall(i,j+1)) {
            j++;
            setPosition();
        }
    }
    public void moveLeft(){
        if(!maze.isWall(i,j-1)) {
            j--;
            setPosition();
        }
    }
    public void moveUp(){
        if(!maze.isWall(i-1,j)) {
            i--;
            setPosition();
        }
    }
    public void moveDown(){
        if(!maze.isWall(i+1,j)) {
            i++;
            setPosition();
        }
    }

}
