package org.example.gamedemo;
import java.util.*;

public class Maze {
    private enum Cell {
        EMPTY_SPACE,WALL,PELLET,POWER_PELLET
    }
    Cell[][] grid;
    private boolean[][] adjMatrix;//to represent a graph
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
        grid = new Cell[ROWS][COLS];
        generateMaze();
    }

    private void generateMaze() {
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
        generateInteriorWalls();

        // Place pellets and power pellets
        placePellets();
        constructAdjMatrix();
    }
    // another interiorWallGenerator needed
    private void generateInteriorWalls() {
        int[][] arr ;
        arr = new int[][]{{2, 4, 5, 7, 8, 9}, {2, 7}, {2, 3, 5, 7, 9}, {5, 9}, {1, 3, 4, 5, 6, 7, 9}, {1, 5}, {3, 5, 7, 8, 9}};
        for(int i =0; i<arr.length; i++){
            for(int j = 0 ; j<arr[i].length ;j++){
                grid[i+2][arr[i][j]] = Cell.WALL;
                grid[ROWS-(i+3)][arr[i][j]] = Cell.WALL;
                grid[i+2][COLS-1-arr[i][j]] = Cell.WALL;
                grid[ROWS-(i+3)][COLS-1-arr[i][j]] = Cell.WALL;
            }
        }
        int[] middle= {1,3,7,17,15,11};
        for(int i=0;i<middle.length;i++)
            grid[9][middle[i]] = Cell.WALL;
        grid[2][9] = Cell.EMPTY_SPACE;
        grid[8][9] = Cell.EMPTY_SPACE;
        grid[10][9] = Cell.EMPTY_SPACE;
        grid[16][9] = Cell.EMPTY_SPACE;
    }
    private void placePellets() {
        Random random = new Random();
        // Place pellets randomly in the maze
        for (int i = 1; i < ROWS - 1; i++) {
            for (int j = 1; j < COLS - 1; j++) {
                if (grid[i][j] != Cell.WALL) {
                    grid[i][j] = Cell.PELLET;// Place a pellet with a probability of 0.3
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
    public void setEmptySpace(int row,int column){
        grid[row][column] = Cell.EMPTY_SPACE;
    }

    /*--------------------------------------------------------------------------------------------------------*/
    //Danger Area.....
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
