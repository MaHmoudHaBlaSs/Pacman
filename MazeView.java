package org.example.gamedemo;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;

public class MazeView extends Pane{
    private static final int CELL_SIZE = 40; // Controls the screen Dimensions (be Careful)
    private Maze maze;
    private String[] gatesUrls = {"Gate1.gif","Gate2.gif"};
    public MazeView(Maze maze) {
        this.maze = maze;

        drawMaze(maze.getMazeNum());
    }
    public Maze getMaze(){
        return maze;
    }
    private void drawMaze(int mazeNum) {
        int rows = maze.getRows();
        int cols = maze.getCols();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze.isWall(i, j))
                {
                    //                Coordinates : X(Rows*Width), Y(Cols*Height)      Dimensions
                    Rectangle wall = new Rectangle(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE-2, CELL_SIZE-2);
                    switch (mazeNum){
                        case 1:
                            wall.setFill(Color.web("#808080").deriveColor(0, 1, 1,1)); // Adjust wall color
                            wall.setStroke(Color.BLACK);
                            break;
                        case 2:
                            wall.setFill(Color.web("#922724").deriveColor(0, 1, 1, 1)); // Adjust wall color
                            wall.setStroke(Color.ORANGE);
                            break;
                        case 3:
                            wall.setFill(Color.web("#c0c0c0").deriveColor(0, 1, 1, 1)); // Adjust wall color
                            wall.setStroke(Color.BLACK);
                            break;
                    }

                    wall.setStrokeWidth(3);
                    wall.setArcHeight(10);
                    wall.setArcWidth(10);
                    getChildren().add(wall);
                }
                else if(maze.isGateIn(i, j)){
                    ImageView gateIn = new ImageView(gatesUrls[0]);
                    gateIn.setFitHeight(CELL_SIZE-10);
                    gateIn.setFitWidth(CELL_SIZE+5);
                    gateIn.setTranslateX(j*CELL_SIZE);
                    gateIn.setTranslateY(i*CELL_SIZE);
                    if(j<9){gateIn.setScaleX(-1);}
                    getChildren().add(gateIn);
                }
                else if(maze.isGateOut(i, j)){
                    ImageView gateOut = new ImageView(gatesUrls[1]);
                    gateOut.setFitHeight(CELL_SIZE-10);
                    gateOut.setFitWidth(CELL_SIZE+5);
                    gateOut.setTranslateX(j*CELL_SIZE);
                    gateOut.setTranslateY(i*CELL_SIZE);
                    if(j<9){gateOut.setScaleX(-1);}
                    getChildren().add(gateOut);
                }
                else if (maze.isPellet(i, j))
                {
                    //Coordinates : X(Rows*Width+.5*CELL) -> Center of the Cell....               Radius
                    Circle pellet = new Circle(j * CELL_SIZE + CELL_SIZE / 2, i * CELL_SIZE + CELL_SIZE / 2, 6);
                    pellet.setFill(Color.YELLOW); // Adjust pellet color
                    pellet.setStroke(Color.BLACK);
                    pellet.setStrokeWidth(1);
                    getChildren().add(pellet);
                }
                else if (maze.isPowerPellet(i, j))
                {
                    // Same as pellet but with larger Radius
                    Circle powerPellet = new Circle(j * CELL_SIZE + CELL_SIZE / 2, i * CELL_SIZE + CELL_SIZE / 2, 9);
                    powerPellet.setFill(Color.ORANGE); // Adjust power pellet color
                    powerPellet.setStrokeWidth(1.3);
                    powerPellet.setStroke(Color.RED);
                    getChildren().add(powerPellet);
                }
                else{
                    //                Coordinates : X(Rows*Width), Y(Cols*Height)      Dimensions
                    Rectangle emptyCell = new Rectangle(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    emptyCell.setFill(Color.TRANSPARENT); // Adjust wall color
                    getChildren().add(emptyCell);
                }
                // Add more conditions to handle Pacman and ghosts
            }
        }
    }
}
