package org.example.gamedemo;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final int CELL_SIZE = 40;
    private static final int BOARD_WIDTH = 760;
    private static final int BOARD_HEIGHT = 760;
    @Override
    public void start(Stage primaryStage) {

        MazeView mazeView = new MazeView(new Maze(BOARD_WIDTH,BOARD_HEIGHT,CELL_SIZE));
        PacMan pacman = new PacMan(1, 1,mazeView); // Our Hero
        Ghost ghost1 = new Ghost(7,8,mazeView);
        ghost1.setMode(1);
        Ghost ghost2 = new Ghost(17,1,mazeView);
        ghost2.setMode(2);
        Ghost ghost3 = new Ghost(17,17,mazeView);
        ghost3.setMode(3);
        Ghost ghost4 = new Ghost(8,9,mazeView);
        ghost4.setMode(4);

        Scene scene = new Scene(mazeView, BOARD_WIDTH, BOARD_HEIGHT);

        // Set up event handling for key presses
        scene.setOnKeyPressed(e->{
            switch(e.getCode()){
                case UP :
                    pacman.moveUp();
                    pacman.rotateToTop();
                    break;
                case DOWN:
                    pacman.moveDown();
                    pacman.rotateToBottom();
                    break;

                case RIGHT:
                    pacman.moveRight();
                    pacman.reflectVerticallyToRight();
                    break;
                case LEFT:
                    pacman.moveLeft();
                    pacman.reflectVerticallyToLeft();
                    break;
            }
        });

        Timeline ghostTM = new Timeline(
                new KeyFrame(Duration.millis(200), e -> {
                    ghost1.moveGhost();
                    ghost2.moveGhost();
                    ghost3.moveGhost();
                    ghost4.moveGhost();
                }));
        ghostTM.setCycleCount(-1);
        ghostTM.play();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pac-Man Game");
        primaryStage.show();
    }


}
