package org.example.gamedemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class PacMan extends GifCharacter {
    private Maze maze ;
    protected MazeView mazeView;
    private int score = 0;
    int Last_press=0 ;    // To set the Start direction to the right
    Timeline Countinos_Motion;
    public PacMan(int startingI,int startingJ,MazeView mazeView){
        this.mazeView = mazeView;
        maze = mazeView.getMaze();
        currentRow = startingI;
        currentColumn = startingJ;

        // Create the ImageView , stick it to the maze
        ImageView gif = new ImageView("pacman02.gif");
        this.getChildren().add(gif);//-----------
        mazeView.getChildren().add(this);
        setGif(gif);

        // Set the initial position
        setPosition();

        // Set the size (adjust as needed)
        gif.setFitWidth(CELL_SIZE+10);
        gif.setFitHeight(CELL_SIZE+10);

        // to control PacMan Motion
        Countinos_Motion = new Timeline(new KeyFrame(Duration.millis(300),e->{
            switch(Last_press){
                case 0 :
                    this.moveRight();
                    this.reflectVerticallyToRight();
                    break;
                case 1:
                    this.moveDown();
                    this.rotateToBottom();
                    break;
                case 2:
                    this.moveUp();
                    this.rotateToTop();
                    break;
                case 3:
                    this.moveLeft();
                    this.reflectVerticallyToLeft();
                    break;
            }
        }));
    }
    // @Override
    public void moveRight(){
        if(!maze.isWall(currentRow,currentColumn+1)) {
            currentColumn++;
            setPosition();
            updateScore();
        }
    }
    //@Override
    public void moveLeft(){
        if(!maze.isWall(currentRow,currentColumn-1)) {
            currentColumn--;
            setPosition();
            updateScore();
        }
    }
    //@Override
    public void moveUp(){
        if(!maze.isWall(currentRow-1,currentColumn)) {
            currentRow--;
            setPosition();
            updateScore();
        }
    }
    //@Override
    public void moveDown(){
        if(!maze.isWall(currentRow+1,currentColumn)) {
            currentRow++;
            setPosition();
            updateScore();
        }
    }

    private void updateScore(){
        if(maze.isPellet(currentRow,currentColumn))
            score += 1;
        else if(maze.isPowerPellet(currentRow,currentColumn))
            score += 3;

        //replace the pallet cell with empty space
        Rectangle emptySpace = new Rectangle(currentColumn * CELL_SIZE, currentRow * CELL_SIZE,CELL_SIZE,CELL_SIZE);
        emptySpace.setFill(Color.WHITE);
        maze.setEmptySpace(currentRow,currentColumn);
        mazeView.getChildren().remove(currentRow* maze.getCols() + currentColumn );
        mazeView.getChildren().add(currentRow* maze.getCols() + currentColumn, emptySpace);
    }

}
