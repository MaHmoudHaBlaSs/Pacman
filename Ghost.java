package org.example.gamedemo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ghost extends Character{
    private final int UP =0;
    private final int DOWN =1;
    private final int LEFT =2;
    private final int RIGHT =3;
    private int direction;
    private int mode = 1;
    private int moves =0;

    public void setMode(int mode) {
        this.mode = mode;
    }
    private boolean stuck = false;
    public Ghost(int startingI,int startingJ,MazeView mazeView){
        super(mazeView, new Image("Ghost1.gif"));
        // MazeView extends Pane class.
        super.mazeView = mazeView;
        maze = mazeView.getMaze();
        i = startingI;
        j = startingJ;

        // Set the size (adjust as needed)
        character_gif.setFitWidth(CELL_SIZE-8);
        character_gif.setFitHeight(CELL_SIZE-8);

        // Set the initial position
        character_gif.setX(j*CELL_SIZE);
        character_gif.setY(i*CELL_SIZE);
    }
    public void moveGhost(){
        switch(mode)
        {
            case 1:
                // TOP - RIGHT - BOTTOM - LEFT
                if(moves == 15) {mode = 2; moves = 0;}
                if(!maze.isWall(i-1,j) && ((direction != DOWN) || isStuck(i,j))) // Check top side
                {
                    moveUp();
                }
                else if(!maze.isWall(i,j+1) && ((direction != LEFT) || (isStuck(i, j)))) // Check right side
                {
                    moveRight();
                }
                else if(!maze.isWall(i+1,j) && ((direction != UP) || isStuck(i,j))) // Check bottom side
                {
                    moveDown();
                }
                else if(!maze.isWall(i, j-1) && ((direction != RIGHT) || (isStuck(i, j)))) // Check left side
                {
                    moveLeft();
                }
                moves++;
            break;
            case 2:
                // BOTTOM - LEFT -TOP - RIGHT
                if(moves == 13) {mode = 3; moves = 0;}
                if(!maze.isWall(i+1,j) && ((direction != UP) || isStuck(i,j))) // Check Bottom side
                {
                    moveDown();
                }
                else if(!maze.isWall(i, j-1) && ((direction != RIGHT) || (isStuck(i, j)))) // Check lef side
                {
                    moveLeft();
                }
                else if(!maze.isWall(i-1,j) && ((direction != DOWN) || isStuck(i,j))) // Check top side
                {
                    moveUp();
                }
                else if(!maze.isWall(i,j+1) && ((direction != LEFT) || (isStuck(i, j)))) // Check right side
                {
                    moveRight();
                }
                moves++;
            break;
            case 3:
                // LEFT - TOP - BOTTOM - RIGHT
                if(moves == 12) {mode = 4; moves = 0;}
                if(!maze.isWall(i, j-1) && ((direction != RIGHT) || (isStuck(i, j)))) // Check lef side
                {
                    moveLeft();
                }
                else if(!maze.isWall(i-1,j) && ((direction != DOWN) || isStuck(i,j))) // Check top side
                {
                    moveUp();
                }
                else if(!maze.isWall(i+1,j) && ((direction != UP) || isStuck(i,j))) // Check Bottom side
                {
                    moveDown();
                }
                else if(!maze.isWall(i,j+1) && ((direction != LEFT) || (isStuck(i, j)))) // Check right side
                {
                    moveRight();
                }
                moves++;
            break;
            case 4:
                // RIGHT - BOTTOM - TOP - LEFT
                if(moves == 14) {mode = 1; moves = 0;}
                if(!maze.isWall(i,j+1) && ((direction != LEFT) || (isStuck(i, j)))) // Check right side
                {
                    moveRight();
                }
                else if(!maze.isWall(i+1,j) && ((direction != UP) || isStuck(i,j))) // Check Bottom side
                {
                    moveDown();
                }
                else if(!maze.isWall(i-1,j) && ((direction != DOWN) || isStuck(i,j))) // Check top side
                {
                    moveUp();
                }
                else if(!maze.isWall(i, j-1) && ((direction != RIGHT) || (isStuck(i, j)))) // Check lef side
                {
                    moveLeft();
                }
                moves++;
            break;
        }
    }

    public boolean isStuck(int i, int j){
        int walls = 0;
        if(maze.isWall(i-1,j)) {walls++;} // Check top side
        if(maze.isWall(i,j+1)) {walls++;} // Check right side
        if(maze.isWall(i+1,j)) {walls++;} // Check bottom side
        if(maze.isWall(i, j-1)) {walls++;}// Check left side
        return walls == 3;
    }
    @Override
    public void moveRight(){
        j += 1;
        setPosition();
        direction = RIGHT;
    }
    @Override
    public void moveLeft(){
        j -= 1;
        setPosition();
        direction = LEFT;
    }
    @Override
    public void moveUp(){
        i -= 1;
        setPosition();
        direction = UP;
    }
    @Override
    public void moveDown(){
        i += 1;
        setPosition();
        direction = DOWN;
    }
}
