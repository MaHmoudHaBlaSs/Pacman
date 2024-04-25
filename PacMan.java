package com.example.pac_man;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class PacMan extends Pane {
    private static final int CELL_SIZE = 40;
    private static final int BOARD_WIDTH =760;
    private static final int BOARD_HEIGHT = 760;
    private Image image;
    private ImageView pacman_gif;
    private boolean reflected = false;
    private int i;
    private int j;
    private Maze maze ;
    private MazeView mazeView;
    private int score = 0;
    public PacMan(int startingI,int startingJ,MazeView mazeView){
        this.mazeView = mazeView;
        maze = mazeView.getMaze();
        i = startingI;
        j = startingJ;

        // Create the ImageView , stick it to the maze
        pacman_gif = new ImageView("pacman02.gif");
        mazeView.getChildren().add(pacman_gif);

        // Set the initial position
        pacman_gif.setX(j*CELL_SIZE-5);
        pacman_gif.setY(i*CELL_SIZE);

        // Set the size (adjust as needed)
        pacman_gif.setFitWidth(CELL_SIZE+10);
        pacman_gif.setFitHeight(CELL_SIZE+10);

    }

    public void moveRight(){
        if(!maze.isWall(i,j+1)) {
            j++;
            setPosition();
            updateScore();
        }
    }
    public void moveLeft(){
        if(!maze.isWall(i,j-1)) {
            j--;
            setPosition();
            updateScore();
        }
    }
    public void moveUp(){
        if(!maze.isWall(i-1,j)) {
            i--;
            setPosition();
            updateScore();
        }
    }
    public void moveDown(){
        if(!maze.isWall(i+1,j)) {
            i++;
            setPosition();
            updateScore();
        }
    }

    public void reflectVerticallyToLeft() {
        // Reset to initial angel
        pacman_gif.setRotate(0);
        // Flip the pacman to the left
        pacman_gif.setScaleX(-1);
        reflected = true;
    }

    public void rotateToBottom() {
        if(reflected){
            pacman_gif.setScaleX(1);
        }
        // Flip the Pacman image horizontally to the bottom
        pacman_gif.setRotate(90);
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

    private void setPosition(){
        pacman_gif.setX(j*CELL_SIZE -5);
        pacman_gif.setY(i*CELL_SIZE);
    }
    private void updateScore(){
        if(maze.isPellet(i,j))
            score += 1;
        else if(maze.isPowerPellet(i,j))
            score += 3;

        //replace the pallet cell with empty space
        Rectangle emptySpace = new Rectangle(j * CELL_SIZE, i * CELL_SIZE,CELL_SIZE,CELL_SIZE);
        emptySpace.setFill(Color.WHITE);
        maze.setEmptySpace(i,j);
        mazeView.getChildren().remove(i* maze.getCols() + j );
        mazeView.getChildren().add(i* maze.getCols() + j, emptySpace);
    }
}
