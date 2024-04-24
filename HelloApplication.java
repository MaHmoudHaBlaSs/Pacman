package org.example.gamedemo;
//abdallah is here
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final double CELL_SIZE = 40;
    private static final int BOARD_WIDTH = 1000;
    private static final int BOARD_HEIGHT = 800;


    @Override
    public void start(Stage primaryStage) {
        PacMan pacman = new PacMan(500, 400);
        Pane root = new Pane();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        // Create Pac-Man
        root.getChildren().add(pacman.getView());

        // Set up event handling for key presses
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    pacman.movePacman(0, -CELL_SIZE/3);
                    pacman.rotateToTop();
                    break;
                case DOWN:
                    pacman.movePacman(0, CELL_SIZE/3);
                    pacman.rotateToBottom();
                    break;
                case LEFT:
                    pacman.movePacman(-CELL_SIZE/3, 0);
                    pacman.reflectVerticallyToLeft();
                    break;
                case RIGHT:
                    pacman.movePacman(CELL_SIZE/3, 0);
                    pacman.reflectVerticallyToRight();
                    break;
                default:
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pac-Man Game");
        primaryStage.show();
    }

}
