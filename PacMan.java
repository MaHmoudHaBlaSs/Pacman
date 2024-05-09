package org.example.gamedemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class PacMan extends Character {
    private Maze maze ;

    public MazeView getMazeView() {
        return mazeView;
    }

    protected MazeView mazeView;
    private int score = 0;
    private int lastPress=0 ;    // To set the Start direction to the right
    private Timeline Countinuous_Motion;

    // create object to control sound effects
    GameSounds sound = new GameSounds();
    public PacMan(int startingI, int startingJ, MazeView mazeView, int gifNumber){
        this.mazeView = mazeView;
        maze = mazeView.getMaze();
        currentRow = startingI;
        currentColumn = startingJ;
        mazeView.getChildren().add(sound);

        // Create the ImageView , stick it to the maze
        ImageView gif = switch(gifNumber){
            case 1 -> new ImageView("PacmanEye.gif");
            case 2 -> new ImageView("pacboy.gif");
            case 3 -> new ImageView("pacwoman.gif");
            default -> null;
        };
        this.getChildren().add(gif);//-----------
        mazeView.getChildren().add(this);
        setGif(gif);

        // Set the initial position
        setPosition();

        // Set the size (adjust as needed)
        gif.setFitWidth(CELL_SIZE-10);
        gif.setFitHeight(CELL_SIZE-10);

        // to control PacMan Motion
        Countinuous_Motion = new Timeline(new KeyFrame(Duration.millis(240), e->{
            switch(lastPress){
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
    public Timeline getCountinuous_Motion() {
        return Countinuous_Motion;
    }
    public int getlastPress(){
        return lastPress;
    }
    public void setlastPress(int lastPress){
        this.lastPress = lastPress;
    }
    public void moveRight(){
        if(!maze.isWall(currentRow,currentColumn+1)) {
            setPosition(currentRow,currentColumn+1); // Internal Change in I and J
            updateScore(); // Deals With New I and J
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
//    private void updateScore(){
//        if(maze.isPellet(currentRow,currentColumn)) {
//            score += 10;
//
//            maze.setPellets(maze.getPellets()-1);
//
//            if(sound.eatPellet.getStatus() == MediaPlayer.Status.PLAYING ){
//                sound.eatPellet.stop();
//                sound.eatPellet.play();
//            }else{
//                sound.eatPellet.play();
//            }
//        }
//        else if(maze.isPowerPellet(currentRow,currentColumn)) {
//            score += 30;
//
//            maze.setPellets(maze.getPellets()-1);
//            if(sound.eatPellet.getStatus() == MediaPlayer.Status.PLAYING ){
//                sound.eatPellet.stop();
//                sound.eatPellet.play();
//            }else{
//                sound.eatPellet.play();
//            }
//        }
//
//
//        //replace the pallet cell with empty space
//        Rectangle emptySpace = new Rectangle(currentColumn * CELL_SIZE, currentRow * CELL_SIZE,CELL_SIZE,CELL_SIZE);
//        emptySpace.setFill(Color.TRANSPARENT);
//        maze.setEmptySpace(currentRow,currentColumn);
//        mazeView.getChildren().remove(currentRow* maze.getCols() + currentColumn );
//        mazeView.getChildren().add(currentRow* maze.getCols() + currentColumn, emptySpace);
//    }
    private void updateScore(){
        if(maze.isPellet(currentRow,currentColumn)) {
            score += 10;
            maze.setPellets(maze.getPellets()-1);

            sound.eatPellet.play();
            sound.eatPellet.stop();
        }
        else if(maze.isPowerPellet(currentRow,currentColumn)) {
            score += 30;
            maze.setPellets(maze.getPellets()-1);

            sound.eatPellet.play();
            sound.eatPellet.stop();
        }
        if(maze.isPellet(currentRow,currentColumn) || maze.isPowerPellet(currentRow,currentColumn)){
            //replace the pallet cell with empty space
            Rectangle emptySpace = new Rectangle(currentColumn * CELL_SIZE, currentRow * CELL_SIZE,CELL_SIZE,CELL_SIZE);
            emptySpace.setFill(Color.TRANSPARENT);
            maze.setEmptySpace(currentRow,currentColumn);
            mazeView.getChildren().remove(currentRow* maze.getCols() + currentColumn );
            mazeView.getChildren().add(currentRow* maze.getCols() + currentColumn, emptySpace);
            // Sound
            if(sound.eatPellet.getStatus() == MediaPlayer.Status.PLAYING ){
                sound.eatPellet.stop();
                sound.eatPellet.play();
            }else{
                sound.eatPellet.play();
            }

        }else if(maze.isGateIn(currentRow, currentColumn)){
            for(int i = 0; i < 2; i++){
                if((maze.getInGates()[i][0] == currentRow)&&(maze.getInGates()[i][1] == currentColumn)){
                    currentRow = maze.getOutGates()[i][0];
                    currentColumn = maze.getOutGates()[i][1];
                    lastPress = maze.outGates[i][2];
                    setPosition();
                }
            }
        }
    }
}
