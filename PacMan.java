package org.example.gamedemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class PacMan extends Character {
    private Maze maze ;
    protected MazeView mazeView;
    private int score = 0;
    private Timeline autoMovement;
    // Object to Control Sound Effects
    GameSounds sound = new GameSounds();
    public PacMan(int startingI, int startingJ, MazeView mazeView, int gifNumber){
        this.mazeView = mazeView;
        maze = mazeView.getMaze();
        currentRow = startingI;
        currentColumn = startingJ;
        direction = Direction.RIGHT; // Default Direction

        // Create the Pacman Gif (As Chosen)
        ImageView gif = switch(gifNumber){
            case 1 -> new ImageView("PacmanEye.gif");
            case 2 -> new ImageView("pacboy.gif");
            case 3 -> new ImageView("pacwoman.gif");
            default -> null;
        };
        this.getChildren().add(gif); // Pacman Extends Character Which Extends Pane
        mazeView.getChildren().add(this); // Now The Gif Is a Child of Pacman and The Pacman Itself Is a Child of MazeView
        setGif(gif);

        // Set the initial position
        setPosition(); // Internally Plays 'positionTl' Timeline

        // Set The Size (Adjust as Needed)
        gif.setFitWidth(CELL_SIZE-10);
        gif.setFitHeight(CELL_SIZE-10);

        // To Control Pacman Motion
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
    }

    /*-----------------Movement Methods-----------------*/
    public void moveRight(){
        if(!maze.isWall(currentRow,currentColumn+1)) {
            setPosition(currentRow,currentColumn+1); // Internal Invoking of moverTl
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
    /*--------------------------------------------------*/
    private void updateScore(){
        if(maze.isPellet(nextRow,nextColumn)) {
            score += 10;
            maze.setPellets(maze.getPellets()-1);
        }
        else if(maze.isPowerPellet(nextRow,nextColumn)) {
            score += 30;
            maze.setPellets(maze.getPellets()-1);
        }

        if(maze.isPellet(nextRow,nextColumn) || maze.isPowerPellet(nextRow,nextColumn)){
            // Replace The Pellet Cell With Empty Space
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

        }
        else if(maze.isGateIn(nextRow, nextColumn)){
            for(int i = 0; i < 2; i++){
                if((maze.getInGates()[i][0] == nextRow)&&(maze.getInGates()[i][1] == nextColumn)){
                    nextRow = maze.getOutGates()[i][0];
                    nextColumn = maze.getOutGates()[i][1];
                    direction = maze.getOutGateDirection()[i]; // Adjusted In Maze Class
                    setPosition();
                }
            }
        }
    }

    /*-----------------Setters / Getters-----------------*/
    public Timeline getAutoMovement() {return autoMovement;}
    public MazeView getMazeView() {return mazeView;}
    /*---------------------------------------------------*/

}
