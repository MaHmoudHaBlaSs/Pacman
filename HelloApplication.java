package org.example.gamedemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final int CELL_SIZE = 40;
    private static final int BOARD_WIDTH = 760;
    private static final int BOARD_HEIGHT = 760;
    private static int ROWS = BOARD_HEIGHT / CELL_SIZE;
    private static int COLS = BOARD_HEIGHT / CELL_SIZE;

    @Override
    public void start(Stage primaryStage) {
        // Create Pac-Man

        MazeView mazeView = new MazeView(new Maze(ROWS,COLS));
        PacMan pacman = new PacMan(1, 5,mazeView);
        Pane root = new Pane();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        root.getChildren().addAll(mazeView.getMazeGroup(),pacman.getView());

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
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pac-Man Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

