package com.example.pac_man;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Maze {
    private static final int EMPTY_SPACE = 0;
    private static final int WALL = 1;
    private static final int PELLET = 2;
    private static final int POWER_PELLET = 3;
    private int[][] grid;
    private final int ROWS ;
    private final int COLS;

    public int getRows() {
        return ROWS;
    }

    public int getCols() {
        return COLS;
    }

    public Maze(int width, int height ,int cellSize) {
        ROWS = height/cellSize;
        COLS = width/cellSize;
        grid = new int[ROWS][COLS];
        generateMaze();
    }

    private void generateMaze() {
        // Generate maze layout with walls, let's fill the outer edges with walls
        for (int i = 0; i < ROWS; i++) {
            grid[i][0] = WALL;
            grid[i][COLS - 1] = WALL;
        }
        for (int j = 0; j < COLS; j++) {
            grid[0][j] = WALL;
            grid[ROWS - 1][j] = WALL;
        }

        // Implement maze generation algorithm to create interior walls
        generateInteriorWalls();

        // Place pellets and power pellets
        placePellets();
    }

    // Implement maze generation algorithm to create interior walls.
    private void generateInteriorWalls() {
        int[][] arr ;
        arr = new int[][]{{2, 4, 5, 7, 8, 9}, {2, 7}, {2, 3, 5, 7, 9}, {5, 9}, {1, 3, 4, 5, 6, 7, 9}, {1, 5}, {3, 5, 7, 8, 9}};
        for(int i =0; i<arr.length; i++){
            for(int j = 0 ; j<arr[i].length ;j++){
                    grid[i+2][arr[i][j]] = WALL;
                    grid[ROWS-(i+3)][arr[i][j]] = WALL;
                    grid[i+2][COLS-1-arr[i][j]] = WALL;
                    grid[ROWS-(i+3)][COLS-1-arr[i][j]] = WALL;
            }
        }
        int[] middle= {1,3,7,17,15,11};
        for(int i=0;i<middle.length;i++)
            grid[9][middle[i]] = WALL;

    }
    private void placePellets() {
        Random random = new Random();
        // Place pellets randomly in the maze
        for (int i = 1; i < ROWS - 1; i++) {
            for (int j = 1; j < COLS - 1; j++) {
                if (grid[i][j] == EMPTY_SPACE && random.nextDouble() < 0.3) {
                    grid[i][j] = PELLET; // Place a pellet with a probability of 0.3
                }
            }
        }

        // Place power pellets at specific locations (e.g., corners)
        grid[1][1] = POWER_PELLET;
        grid[1][COLS - 2] = POWER_PELLET;
        grid[ROWS - 2][1] = POWER_PELLET;
        grid[ROWS - 2][COLS - 2] = POWER_PELLET;
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
    public void setEmptySpace(int row,int column){
        grid[row][column] = EMPTY_SPACE;
    }

}
