package com.example.pac_man;
import java.util.*;

public class Maze {
    private enum Cell {
        EMPTY_SPACE, WALL, PELLET, POWER_PELLET, GATE_IN, GATE_OUT
    }

    Cell[][] grid;
    private boolean[][] adjMatrix;//to represent a graph
    private final int ROWS ;
    private final int COLS;
    private int mazeNum;
    private int pellets = 0; // for 4 Power Pellets in any map
    protected int[][] inGates = new int[2][2]; // Array of In Gate Indices
    protected int[][] outGates = new int[2][3];; // Array of Out Gate Indices
    public Maze(int width, int height , int cellSize, int mazeNum) {
        ROWS = height/cellSize;
        COLS = width/cellSize;
        this.mazeNum = mazeNum;
        grid = new Cell[ROWS][COLS];
        generateMaze(mazeNum);
    }

    public int getRows() {
        return ROWS;
    }

    public int getMazeNum() {
        return mazeNum;
    }

    public int getCols() {
        return COLS;
    }

    public void setPellets(int pellets) {this.pellets = pellets;}
    public int getPellets() {
        return pellets;
    }

    public int[][] getInGates() {return inGates;}
    public int[][] getOutGates() {return outGates;}

    private void generateMaze(int mazeNum) {
        // Generate maze layout with walls, let's fill the outer edges with walls
        for (int i = 0; i < ROWS; i++) {
            grid[i][0] = Cell.WALL;
            grid[i][COLS - 1] = Cell.WALL;
        }
        for (int j = 0; j < COLS; j++) {
            grid[0][j] = Cell.WALL;
            grid[ROWS - 1][j] = Cell.WALL;
        }

        // Implement maze generation algorithm to create interior walls
        generateInteriorWalls(mazeNum);

        // Place pellets and power pellets
        placePellets();
        constructAdjMatrix();
    }
    // another interiorWallGenerator needed
    private void generateInteriorWalls(int mazeNum) {
        int[][] arr = switch (mazeNum){
            case 1 -> new int[][] {{2, 4, 5, 7, 8, 9}, {2, 7}, {2, 3, 5, 7, 9}, {5, 9}, {1, 3, 4, 5, 6, 7, 9}, {1, 5}, {3, 5, 7, 8, 9}};
            case 2 -> new int[][] {{2, 4, 5, 6, 7, 8, 9}, {2, 5}, {2, 3, 5, 7, 8}, {2, 8}, {2, 3, 5, 6, 8}, {5,8}, {2, 4, 5, 7, 8}};
            case 3 -> new int[][]{{2, 3, 5, 6, 7, 9}, {0}, {2, 3, 5, 7,8, 9}, {5, 9}, {2, 3, 5, 6, 7, 9}, {3, 5}, {1,3, 5, 7, 8}};
            default -> null;
        };
        for(int i =0; i<arr.length; i++){
            for(int j = 0 ; j<arr[i].length ;j++){
                grid[i+2][arr[i][j]] = Cell.WALL;
                grid[ROWS-(i+3)][arr[i][j]] = Cell.WALL;
                grid[i+2][COLS-1-arr[i][j]] = Cell.WALL;
                grid[ROWS-(i+3)][COLS-1-arr[i][j]] = Cell.WALL;
            }
        }
        int[] middle= {1,3,7,17,15,11};
        for(int i=0;i<middle.length;i++){grid[9][middle[i]] = Cell.WALL;}

        switch (mazeNum){
            case 1:
                grid[2][9] = Cell.EMPTY_SPACE;
                grid[8][9] = Cell.EMPTY_SPACE;
                grid[10][9] = Cell.EMPTY_SPACE;
                grid[16][9] = Cell.EMPTY_SPACE;
                grid[8][1] = Cell.GATE_IN; inGates[0][0] = 8; inGates[0][1] = 1;
                grid[10][17] = Cell.GATE_IN;inGates[1][0] = 10; inGates[1][1] = 17;
                grid[8][17] = Cell.GATE_OUT;outGates[0][0] = 8; outGates[0][1] = 17;outGates[0][2] = 0;
                grid[10][1] = Cell.GATE_OUT;outGates[1][0] = 10; outGates[1][1] = 1; outGates[1][2] =3;
                break;
            case 2:
                grid[2][9] = Cell.EMPTY_SPACE;
                grid[9][7] = Cell.EMPTY_SPACE;
                grid[9][11] = Cell.EMPTY_SPACE;
                grid[8][2] = Cell.EMPTY_SPACE;
                grid[10][2] = Cell.EMPTY_SPACE;
                grid[8][16] = Cell.EMPTY_SPACE;
                grid[10][16] = Cell.EMPTY_SPACE;
                grid[9][4] = Cell.GATE_IN; inGates[0][0] = 9; inGates[0][1] = 4;
                grid[9][14] = Cell.GATE_IN; inGates[1][0] = 9; inGates[1][1] = 14;
                grid[13][15] = Cell.GATE_OUT; outGates[0][0] = 13; outGates[0][1] = 15;outGates[0][2] = 0;
                grid[5][3] = Cell.GATE_OUT; outGates[1][0] = 5; outGates[1][1] = 3;outGates[1][2] =3;
                break;
            case 3:
                grid[1][9] = Cell.WALL;
                grid[17][9] = Cell.WALL;
                grid[10][9] = Cell.WALL;
                grid[9][3] = Cell.EMPTY_SPACE;
                grid[9][15] = Cell.EMPTY_SPACE;
                grid[10][9] = Cell.EMPTY_SPACE;
                grid[9][1] = Cell.GATE_IN; inGates[0][0] = 9; inGates[0][1] = 1;
                grid[9][17] = Cell.GATE_OUT; outGates[0][0] = 9; outGates[0][1] = 17;
                break;
        }
    }
    private void placePellets() {
        // Place pellets in the maze
        for (int i = 1; i < ROWS - 1; i++) {
            for (int j = 1; j < COLS - 1; j++) {
                if (grid[i][j] != Cell.WALL && grid[i][j] != Cell.GATE_IN && grid[i][j] != Cell.GATE_OUT) {
                    grid[i][j] = Cell.PELLET;// Place a pellet
                    pellets++;
                }
            }
        }

        // Place power pellets at specific locations (e.g., corners)
        grid[1][1] = Cell.POWER_PELLET;
        grid[1][COLS - 2] = Cell.POWER_PELLET;
        grid[ROWS - 2][1] = Cell.POWER_PELLET;
        grid[ROWS - 2][COLS - 2] = Cell.POWER_PELLET;
    }
    public boolean isWall(int x, int y) {
        return grid[x][y] == Cell.WALL;
    }
    public boolean isPellet(int x, int y) {
        return grid[x][y] == Cell.PELLET;
    }
    public boolean isPowerPellet(int x, int y) {
        return grid[x][y] == Cell.POWER_PELLET;
    }
    public boolean isGateIn(int x, int y) {return grid[x][y] == Cell.GATE_IN;}
    public boolean isGateOut(int x, int y) {return grid[x][y] == Cell.GATE_OUT;}
    public void setEmptySpace(int row,int column){
        grid[row][column] = Cell.EMPTY_SPACE;
    }
    public boolean isFinishedMap(){
        return pellets == 0;
    }


    //-------------------------------------------------Danger Area------------------------------------------/
    //Directed Ghost Helper Methods.
    public int indicesToCell(int row,int column){
        return row*ROWS + column;
    }
    public int[] cellToIndices(int cell){
        return new int[] {cell/COLS , cell%COLS};
    }
    private void constructAdjMatrix(){
        adjMatrix= new boolean[ROWS*COLS][ROWS*COLS];
        for(int i=1;i<ROWS-1;i++){
            for(int j=1;j<COLS-1;j++){
                if(!isWall(i,j)){
                    if(!isWall(i,j+1)){
                        adjMatrix[i*COLS+j][i*COLS+j+1] = true;
                        adjMatrix[i*COLS+j+1][i*COLS+j] = true;
                    }
                    if(!isWall(i+1,j)){
                        adjMatrix[(i+1)*COLS+j][i*COLS+j] = true;
                        adjMatrix[i*COLS+j][(i+1)*COLS+j] = true;

                    }
                }
            }
        }

    } //Helper Method
    public List<Integer> findShortestPath(int start, int end) {
        int size = adjMatrix.length;
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        int[] parent = new int[size];
        Arrays.fill(parent, -1);

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current == end) {
                break;
            }

            for (int neighbor = 0; neighbor < size; neighbor++) {
                if (adjMatrix[current][neighbor] && !visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parent[neighbor] = current;
                }
            }
        }

        if (!visited.contains(end)) {
            return null; // No path found
        }

        // Retrieve the shortest path by tracing back from the destination element to the starting element
        List<Integer> path = new ArrayList<>();
        int node = end;
        while (node != -1) {
            path.add(node);
            node = parent[node];
        }
        Collections.reverse(path);

        return path;
    }
}
