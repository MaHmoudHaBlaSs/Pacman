package org.example.gamedemo;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import org.example.gamedemo.HelloApplication;

public class PacMan extends Node{
    private static final int CELL_SIZE = 40;
    private static final int BOARD_WIDTH = 1000;
    private static final int BOARD_HEIGHT = 800;
    private Image image;
    private ImageView pacman_gif;
    private double xPos;
    private double yPos;
    private boolean reflected = false;

    public PacMan(int x, int y) {
        // Load the Pacman GIF
        image = new Image("https://art.pixilart.com/58c5d6cfa07837f.gif");

        // Create the ImageView
        pacman_gif = new ImageView(image);

        // Set the initial position
        pacman_gif.setX(x);
        pacman_gif.setY(y);
        xPos = pacman_gif.getX();
        yPos = pacman_gif.getY();

        // Set the size (adjust as needed)
        pacman_gif.setFitWidth(100);
        pacman_gif.setFitHeight(100);
    }

    public ImageView getView() {
        return pacman_gif;
    }

    public void movePacman(double dx, double dy) {
        double newXPosition = pacman_gif.getX() + dx;
        double newYPosition = pacman_gif.getY() + dy;
        if ((newXPosition <= BOARD_WIDTH - (CELL_SIZE / 3)) &&
                newYPosition <= BOARD_HEIGHT - (CELL_SIZE / 3)) {
            pacman_gif.setX(newXPosition);
            pacman_gif.setY(newYPosition);
            this.xPos = newXPosition;
            this.yPos = newYPosition;
        }
    }

    public void reflectVerticallyToLeft() {
        // Reset to initial angel
        pacman_gif.setRotate(0);
        // Flip the pacman to the left
        pacman_gif.setScaleX(-1);
        reflected = true;
    }

    public void reflectVerticallyToRight() {
        // Flip the Pacman image vertically to the right
        if(reflected){
            pacman_gif.setScaleX(1);
        }
        // Reset to initial angel
        pacman_gif.setRotate(0);
    }

    public void rotateToTop() {
        if(reflected){
            pacman_gif.setScaleX(1);
        }
        // Flip the Pacman image horizontally to the top
        pacman_gif.setRotate(270);
    }

    public void rotateToBottom() {
        if(reflected){
            pacman_gif.setScaleX(1);
        }
        // Flip the Pacman image horizontally to the bottom
        pacman_gif.setRotate(90);
    }
}
