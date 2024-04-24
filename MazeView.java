package org.example.gamedemo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;

public class MazeView {
    private static final int CELL_SIZE = 20; // Controls the screen Dimensions (be Careful)
    private static final int BOARD_WIDTH = 1000;
    private static final int BOARD_HEIGHT = 800;
    private static final int EMPTY_SPACE = 0;
    private static final int WALL = 1;
    private static final int PELLET = 2;
    private static final int POWER_PELLET = 3;
    private Maze maze;
    private Group mazeGroup;

    public MazeView(Maze maze) {
        this.maze = maze;
        mazeGroup = new Group();
        drawMaze();
    }

    public Group getMazeGroup() {
        return mazeGroup;
    }

    private void drawMaze() {
        int rows = maze.getRows();
        int cols = maze.getCols();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze.isWall(i, j))
                {
                    //                Coordinates : X(Rows*Width), Y(Cols*Height)      Dimensions
                    Rectangle wall = new Rectangle(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    wall.setFill(Color.BLUE); // Adjust wall color
                    wall.setStroke(Color.BLACK);
                    wall.setStrokeWidth(3);
                    wall.setArcHeight(10);
                    wall.setArcWidth(10);
                    mazeGroup.getChildren().add(wall);
                }
                else if (maze.isPellet(i, j))
                {
                    //                Coordinates : X(Rows*Width+.5*CELL) -> Center of the Cell....               Radius
                    Circle pellet = new Circle(j * CELL_SIZE + CELL_SIZE / 2, i * CELL_SIZE + CELL_SIZE / 2, 4);
                    pellet.setFill(Color.YELLOW); // Adjust pellet color
                    pellet.setStroke(Color.BLACK);
                    pellet.setStrokeWidth(1);
                    mazeGroup.getChildren().add(pellet);
                }
                else if (maze.isPowerPellet(i, j))
                {
                    // Same as pellet but with larger Radius
                    Circle powerPellet = new Circle(j * CELL_SIZE + CELL_SIZE / 2, i * CELL_SIZE + CELL_SIZE / 2, 5);
                    powerPellet.setFill(Color.ORANGE); // Adjust power pellet color
                    powerPellet.setStrokeWidth(1.3);
                    powerPellet.setStroke(Color.RED);
                    mazeGroup.getChildren().add(powerPellet);
                }
                // Add more conditions to handle Pacman and ghosts
            }
        }
    }
}

