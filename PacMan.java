package org.example.gamedemo;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import org.example.gamedemo.HelloApplication;

public class PacMan extends Node{
    private static final int CELL_SIZE = 40;
    private static final int BOARD_WIDTH = 1000;
    private static final int BOARD_HEIGHT = 800;
    private Image image;
    private ImageView pacman_gif;
    private double xPos;
    private double yPos;
    private boolean reflected = false;
    private Maze maze ;

    public PacMan(int startingI,int startingJ,MazeView mazeView){
        maze = mazeView.getMaze();
        i = startingI;
        j = startingJ;

        // Create the ImageView
        pacman_gif = new ImageView("pacman02.gif");

        // Set the initial position
        pacman_gif.setX(j*CELL_SIZE-5);
        pacman_gif.setY(i*CELL_SIZE);

        // Set the size (adjust as needed)
        pacman_gif.setFitWidth(CELL_SIZE+10);
        pacman_gif.setFitHeight(CELL_SIZE+10);


    }


    public ImageView getView() {
        return pacman_gif;
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
    }    public void moveUp(){
        if(!maze.isWall(i-1,j)) {
            i--;
            setPosition();
        }
    }    public void moveDown(){
        if(!maze.isWall(i+1,j)) {
            i++;
            setPosition();
        }
    }


    public void reflectVerticallyToLeft() {
        // Reset to initial angel
        pacman_gif.setRotate(0);
        // Flip the pacman to the left
        pacman_gif.setScaleX(-1);
        reflected = true;
    }

    public void reflectVerticallyToRight() {
        // Flip the Pacman image vertically to the right
        if(reflected){
            pacman_gif.setScaleX(1);
        }
        // Reset to initial angel
        pacman_gif.setRotate(0);
    }

    public void rotateToTop() {
        if(reflected){
            pacman_gif.setScaleX(1);
        }
        // Flip the Pacman image horizontally to the top
        pacman_gif.setRotate(270);
    }

    public void rotateToBottom() {
        if(reflected){
            pacman_gif.setScaleX(1);
        }
        // Flip the Pacman image horizontally to the bottom
        pacman_gif.setRotate(90);
    }
}
