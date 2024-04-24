package org.example.gamedemo;

import java.util.Random;

public class Maze {
    private static final int CELL_SIZE = 20;
    private static final int BOARD_WIDTH = 1000;
    private static final int BOARD_HEIGHT = 800;
    private static final int EMPTY_SPACE = 0;
    private static final int WALL = 1;
    private static final int PELLET = 2;
    private static final int POWER_PELLET = 3;

    private int[][] grid;
    private int rows;
    private int cols;

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new int[rows][cols];
        generateMaze();
    }

    private void generateMaze() {
        // Generate maze layout with walls
        // For simplicity, let's fill the outer edges with walls
       for (int i = 0; i < rows; i++) {
           grid[i][0] = WALL;
           grid[i][cols - 1] = WALL;
       }
       for (int j = 0; j < cols; j++) {
           grid[0][j] = WALL;
           grid[rows - 1][j] = WALL;
       }

        // Implement maze generation algorithm to create interior walls
        // For example, you can use a randomized algorithm like recursive division
        // or depth-first search to create maze walls.
        generateInteriorWalls();

        // Place pellets and power pellets
        placePellets();
    }

    // Implement maze generation algorithm to create interior walls
    // For example, you can use a randomized algorithm like recursive division
    // or depth-first search to create maze walls.
    private void generateInteriorWalls() {

    }

    private void placePellets() {
        Random random = new Random();
        // Place pellets randomly in the maze
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (grid[i][j] == EMPTY_SPACE && random.nextDouble() < 0.3) {
                    grid[i][j] = PELLET; // Place a pellet with a probability of 0.3
                }
            }
        }

        // Place power pellets at specific locations (e.g., corners)
        grid[1][1] = POWER_PELLET;
        grid[1][cols - 2] = POWER_PELLET;
        grid[rows - 2][1] = POWER_PELLET;
        grid[rows - 2][cols - 2] = POWER_PELLET;
    }

    public boolean isWall(int x, int y) {
        return grid[x][y] == WALL;
    }

    public boolean isPellet(int x, int y) {
        return grid[x][y] == PELLET;
    }

    public boolean isPowerPellet(int x, int y) {
        return grid[x][y] == POWER_PELLET;
    }

}
