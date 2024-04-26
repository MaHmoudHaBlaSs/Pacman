package org.example.gamedemo;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class PacMan extends Character{
    private int score = 0;

    public PacMan(int startingI,int startingJ,MazeView mazeView){
        super(mazeView, new Image("pacman.gif"));
        // MazeView extends Pane class.
        super.mazeView = mazeView;
        maze = mazeView.getMaze();
        i = startingI;
        j = startingJ;

        // Set the size (adjust as needed)
        character_gif.setFitWidth(CELL_SIZE+10);
        character_gif.setFitHeight(CELL_SIZE+10);

        // Set the initial position
        character_gif.setX(j*CELL_SIZE-5);
        character_gif.setY(i*CELL_SIZE);

    }

    @Override
    public void moveRight(){
        if(!maze.isWall(i,j+1)) {
            j++;
            setPosition();
            updateScore();
        }
    }
    @Override
    public void moveLeft(){
        if(!maze.isWall(i,j-1)) {
            j--;
            setPosition();
            updateScore();
        }
    }
    @Override
    public void moveUp(){
        if(!maze.isWall(i-1,j)) {
            i--;
            setPosition();
            updateScore();
        }
    }
    @Override
    public void moveDown(){
        if(!maze.isWall(i+1,j)) {
            i++;
            setPosition();
            updateScore();
        }
    }

    private void updateScore(){
        if(maze.isPellet(i,j))
            score += 50;
        else if(maze.isPowerPellet(i,j))
            score += 100;

        //replace the pallet cell with empty space (empty Rectangle)
        Rectangle emptySpace = new Rectangle(j * CELL_SIZE, i * CELL_SIZE,CELL_SIZE,CELL_SIZE);
        emptySpace.setFill(Color.TRANSPARENT);
        maze.setEmptySpace(i,j);
        mazeView.getChildren().remove(i* maze.getCols() + j );
        mazeView.getChildren().add(i* maze.getCols() + j, emptySpace);
    }
}
