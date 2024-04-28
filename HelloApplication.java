package org.example.gamedemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

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

        //adding backgroud sound



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
                new KeyFrame(Duration.millis(400), event -> {
                    Media sound = new Media(new File("C:\\Users\\Abdal\\IdeaProjects\\helloapplication\\src\\main\\resources\\pacman_chomp.mp3").toURI().toString());
                    MediaPlayer bckgmp3 = new MediaPlayer(sound);
                    bckgmp3.setCycleCount(bckgmp3.INDEFINITE);
                    bckgmp3.play();
                    ghost1.moveGhost();
                    ghost2.moveGhost();
                    ghost3.moveGhost();
                    ghost4.moveGhost();
                }));
        ghostTM.setCycleCount(-1);
        ghostTM.play();

        Timeline checklife = new Timeline(
                new KeyFrame(Duration.millis(2 ), e -> {

                    if((pacman.getI()==ghost1.getI()&&pacman.getJ()==ghost1.getJ())
                       ||(pacman.getI()==ghost2.getI()&&pacman.getJ()==ghost2.getJ())
                       ||(pacman.getI()==ghost3.getI()&&pacman.getJ()==ghost3.getJ())
                       ||(pacman.getI()==ghost4.getI()&&pacman.getJ()==ghost4.getJ())){
                        mazeView.getChildren().remove(pacman.character_gif);
                        ghostTM.stop();

                    }
                }));
        checklife.setCycleCount(-1);
        checklife.play();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pac-Man Game");
        primaryStage.show();
    }


}
