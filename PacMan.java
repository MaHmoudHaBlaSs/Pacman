package com.example.pac_man;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class PacMan extends Character {
    private Maze maze ;
    protected MazeView mazeView;
    private int score = 0;
    private Text scoreTxt ;
    private Timeline autoMovement;
    private Timeline positionDelayer;
    GameSounds sound = new GameSounds();
    public PacMan(int startingI, int startingJ, MazeView mazeView, int gifNumber){
        this.mazeView = mazeView;
        maze = mazeView.getMaze();
        currentRow = startingI;
        currentColumn = startingJ;
        direction = Direction.RIGHT;
        mazeView.getChildren().add(sound);

        // Create the ImageView , stick it to the maze
        ImageView gif = switch(gifNumber){
            case 1 -> new ImageView("PacmanEye.gif");
            case 2 -> new ImageView("pacboy.gif");
            case 3 -> new ImageView("pacwoman.gif");
            default -> null;
        };
        this.getChildren().add(gif);
        mazeView.getChildren().add(this);
        setGif(gif);

        // Set the initial position
        setPosition();

        // Set the size (adjust as needed)
        gif.setFitWidth(CELL_SIZE-10);
        gif.setFitHeight(CELL_SIZE-10);

        setMovementAnimations();

    }
    private void setMovementAnimations(){
        // to control PacMan Motion
        autoMovement = new Timeline(new KeyFrame(Duration.millis(240), e->{
            switch(direction){
                case RIGHT :
                    this.moveRight();
                    this.reflectVerticallyToRight();
                    break;
                case DOWN:
                    this.moveDown();
                    this.rotateToBottom();
                    break;
                case UP:
                    this.moveUp();
                    this.rotateToTop();
                    break;
                case LEFT:
                    this.moveLeft();
                    this.reflectVerticallyToLeft();
                    break;
            }
        }));

        // used to deal with gates,Changes Character Gif Coordinates Through a Certain Amount of Time (250ms)
        positionDelayer = new Timeline(new KeyFrame(Duration.millis(250),e->{
            getGif().setX(currentColumn*CELL_SIZE+2);
            getGif().setY(currentRow*CELL_SIZE);}
        ));
    }
    public Timeline getAutoMovement() {
        return autoMovement;
    }
    public void moveRight(){
        if(!maze.isWall(currentRow,currentColumn+1)) {
            setPosition(currentRow,currentColumn+1);
            updateScore();
        }
    }
    public void moveLeft(){
        if(!maze.isWall(currentRow,currentColumn-1)) {
            setPosition(currentRow,currentColumn-1);
            updateScore();
        }
    }
    public void moveUp(){
        if(!maze.isWall(currentRow-1,currentColumn)) {
            setPosition(currentRow-1,currentColumn);
            updateScore();
        }
    }
    public void moveDown(){
        if(!maze.isWall(currentRow+1,currentColumn)) {
            setPosition(currentRow+1,currentColumn);
            updateScore();
        }
    }
    private void updateScore(){
        if(maze.isPellet(nextRow,nextColumn)) {
            score += 10;
            maze.setPellets(maze.getPellets()-1);
            mazeView.updateScoreText(score);
        }
        else if(maze.isPowerPellet(nextRow,nextColumn)) {
            score += 30;
            maze.setPellets(maze.getPellets()-1);
            mazeView.updateScoreText(score);
        }

        if(maze.isPellet(nextRow,nextColumn) || maze.isPowerPellet(nextRow,nextColumn)){
            //replace the pellet cell with empty space
            Rectangle emptySpace = new Rectangle(nextColumn * CELL_SIZE, nextRow * CELL_SIZE,CELL_SIZE,CELL_SIZE);
            emptySpace.setFill(Color.TRANSPARENT);
            maze.setEmptySpace(nextRow,nextColumn);
            mazeView.getChildren().remove(nextRow* maze.getCols() + nextColumn );
            mazeView.getChildren().add(nextRow* maze.getCols() + nextColumn, emptySpace);

            // Sound
            if(sound.eatPellet.getStatus() == MediaPlayer.Status.PLAYING ){
                sound.eatPellet.stop();
                sound.eatPellet.play();
            }else{
                sound.eatPellet.play();
            }

        }else if(maze.isGateIn(nextRow, nextColumn)){
            for(int i = 0; i < 2; i++){
                if((maze.getInGates()[i][0] == nextRow)&&(maze.getInGates()[i][1] == nextColumn)){
                    nextRow = maze.getOutGates()[i][0];
                    nextColumn = maze.getOutGates()[i][1];
                    direction = maze.outGateDireciton[i];
                    setDelayedPosition();
                }
            }
        }
    }
    public MazeView getMazeView() {
        return mazeView;
    }
    private void setDelayedPosition(){
        positionDelayer.play();
    }

}
