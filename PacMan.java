package org.example.gamedemo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class PacMan extends Character {
    private Maze maze ;
    protected MazeView mazeView;
    private int score = 0;
    public PacMan(int startingI,int startingJ,MazeView mazeView){
        this.mazeView = mazeView;
        maze = mazeView.getMaze();
        currentRow = startingI;
        currentColumn = startingJ;

        // Create the ImageView , stick it to the maze
        ImageView gif = new ImageView("pacman.gif");
        this.getChildren().add(gif);
        mazeView.getChildren().add(this);
        setGif(gif);

        // Set the initial position
        setPosition();

        // Set the size (adjust as needed)
        gif.setFitWidth(CELL_SIZE+10);
        gif.setFitHeight(CELL_SIZE+10);

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

    // Level upgrade needed
    private void updateScore(){
        if(maze.isPellet(currentRow,currentColumn))
            score += 50;
        else if(maze.isPowerPellet(currentRow,currentColumn))
            score += 150;

        //replace the pallet cell with empty space
        Rectangle emptySpace = new Rectangle(currentColumn * CELL_SIZE, currentRow * CELL_SIZE,CELL_SIZE,CELL_SIZE);
        emptySpace.setFill(Color.TRANSPARENT);
        maze.setEmptySpace(currentRow,currentColumn);
        mazeView.getChildren().remove(currentRow* maze.getCols() + currentColumn );
        mazeView.getChildren().add(currentRow* maze.getCols() + currentColumn, emptySpace);
    }

}
