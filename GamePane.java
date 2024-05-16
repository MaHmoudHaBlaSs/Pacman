package com.example.pac_man;

import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GamePane extends Pane {
    public static final int CELL_SIZE = 40;
    public static final int BOARD_WIDTH = 760;
    public static final int BOARD_HEIGHT = 760;
    private MazeView mazeView;
    private PacMan pacman ;
    GameSounds sound ;
    private final Ghost[] ghosts;
    boolean death = false;   // to know pacman is still alive or die
    private boolean win = false;      // to know if user win
    private Stage stage ;
    private Scene mainScene;
    private Scene gameScene;
    private Timeline positionChecker;

    public GamePane( int mapNumber, GameSounds sound , int level, int pacmanGifNum,Stage stage,Scene mainScene ) {
        this.sound = sound;
        this.stage = stage;
        this.mainScene = mainScene;

        mazeView = new MazeView(new Maze(BOARD_WIDTH, BOARD_HEIGHT, CELL_SIZE, mapNumber));
        pacman = new PacMan(1, 1, mazeView, pacmanGifNum);
        ghosts = createGhosts(mazeView, level);
        getChildren().addAll(createBackground(BOARD_WIDTH,BOARD_HEIGHT,mapNumber),mazeView);
        gameScene = new Scene(this,BOARD_WIDTH,BOARD_HEIGHT);

        movePacman();
        checkLife();

    }


    // Creating Game Background
    private ImageView createBackground(double width, double height, int mazeNum) {
        String[] mazes = new String[]{"GameBackground1.gif", "GameBackground2.gif", "GameBackground3.gif"};
        ImageView background = new ImageView(mazes[mazeNum - 1]);
        background.setFitWidth(width);
        background.setFitHeight(height);
        return background;
    }
    // Returning ghosts (Array) created based on level
    public Ghost[] createGhosts(MazeView mazeView, int level) {
        Ghost[] ghosts = new Ghost[4];
        switch (level) {
            case 1, 2, 3:
                ghosts[0] = new Ghost(17, 17, mazeView, level);
                ghosts[0].setMode(1);
                ghosts[1] = new Ghost(17, 1, mazeView, level);
                ghosts[1].setMode(2);
                ghosts[2] = new Ghost(1, 17, mazeView, level);
                ghosts[2].setMode(3);
                ghosts[3] = new Ghost(9, 9, mazeView, level);
                break;
            case 4,5:
                ghosts[0] = new Ghost(17, 17, mazeView, 3);
                ghosts[0].setMode(1);
                ghosts[1] = new Ghost(17, 1, mazeView, 3);
                ghosts[1].setMode(2);
                ghosts[2] = new Ghost(1, 17, mazeView, 3);
                ghosts[2].setMode(3);
                ghosts[3] = new Ghost(9, 9, mazeView, level);
                break;
        }
        return ghosts;
    }
    // Checking pacman's Life (Timeline)
    private void checkLife() {
        death = false;
        win = false;
        sound.deathSound.stop();
        sound.winSound.stop();
        positionChecker = new Timeline(new KeyFrame(Duration.millis(40), e -> {
            for (Ghost ghost : ghosts) {
                if (ghost.getCurrentColumn() == pacman.getCurrentColumn() && ghost.getCurrentRow() == pacman.getCurrentRow()) {
                    death = true;
                    ghost.ghostMovement.stop();
                }
            }
            if (death || pacman.getMazeView().getMaze().isFinishedMap()) {
                startEndScene(death);
            }
        }));
        positionChecker.setCycleCount(-1);
        positionChecker.play();
    }
    // Responsible for moving the pacman (Timeline)
    public void movePacman() {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT:
                    pacman.setDirection(Direction.RIGHT);    //to set last press right
                    break;
                case DOWN:
                    pacman.setDirection(Direction.DOWN);    //to set last press down
                    break;
                case UP:
                    pacman.setDirection(Direction.UP);    //to set last press up
                    break;
                case LEFT:
                    pacman.setDirection(Direction.LEFT);    //to set last press left
                    break;
            }
        });

        pacman.getAutoMovement().setCycleCount(-1);
        pacman.getAutoMovement().play();
    }
    // Viewing Ending Screens [Win - Game Over]
    private void startEndScene(boolean isDeath) {
        positionChecker.stop();

        ImageView[] endingImgs = {new ImageView("GameoverScreen.gif"), new ImageView("WinScreen.jpg")};
        ScaleTransition[] imgsTransition = new ScaleTransition[2];

        for (int i = 0; i < 2; i++) {
            // Setting Image Properties
            endingImgs[i].setFitHeight(5);
            endingImgs[i].setFitWidth(5);
            endingImgs[i].setPreserveRatio(true);
            endingImgs[i].setLayoutX(380);
            endingImgs[i].setLayoutY(380);
            // Setting Image's Transition Effect
            imgsTransition[i] = new ScaleTransition();
            imgsTransition[i].setNode(endingImgs[i]);
            imgsTransition[i].setDuration(Duration.seconds(.5));
            imgsTransition[i].setToX(153);
            imgsTransition[i].setToY(153);

            this.getChildren().add(endingImgs[i]);
            endingImgs[i].setVisible(false);
        }

        FadeTransition[] txtsFadeTransition = new FadeTransition[3];
        StrokeTransition[] txtsStrokeTransition = new StrokeTransition[3];
        Text[] endGameTexts = initializeEndGameTexts(txtsStrokeTransition, txtsFadeTransition);

        if (isDeath) {
            handleGameOver(endingImgs, endGameTexts, txtsFadeTransition, txtsStrokeTransition, imgsTransition);
        } else {
            handleGameWin(endingImgs, endGameTexts, txtsFadeTransition, txtsStrokeTransition, imgsTransition);
        }
    }

    // Set Winning Screen With All Effects Related.
    private void handleGameWin(ImageView[] endingImgs, Text[] endGameTexts, FadeTransition[] txtsFadeTransition,
                               StrokeTransition[] txtsStrokeTransition, ScaleTransition[] imgsTransition) {
        pacman.getAutoMovement().stop();
        pacman.getChildren().remove(pacman.getGif());
        this.getChildren().remove(pacman);
        // Win
        endingImgs[1].setVisible(true);
        imgsTransition[1].play();
        imgsTransition[1].setOnFinished(event1 -> {
            // Press Enter Text
            endGameTexts[0].setVisible(true);
            txtsFadeTransition[0].play();
            txtsStrokeTransition[0].play();
            // Press ESC Text
            endGameTexts[1].setVisible(true);
            txtsFadeTransition[1].play();
            txtsStrokeTransition[1].play();
            // You Won Text
            endGameTexts[2].setVisible(true);
            txtsFadeTransition[2].play();
            txtsStrokeTransition[2].play();

            gameScene.setOnKeyPressed(keyEvent -> {
                switch (keyEvent.getCode()) {
                    case ENTER -> {
                        stage.setScene(mainScene);
                        sound.winSound.stop();
                    }
                    case ESCAPE -> stage.close();
                }

            });
        });
        positionChecker.stop();
        sound.winSound.play();
        win = false;
    }

    // Setting Game Over Screen With All Effects Related.
    private void handleGameOver( ImageView[] endingImgs, Text[] endGameTexts, FadeTransition[] txtsFadeTransition,
                                 StrokeTransition[] txtsStrokeTransition, ScaleTransition[] imgsTransition) {
        sound.deathSound.play();
        death = false;

        pacman.getAutoMovement().stop();
        pacman.getChildren().remove(pacman.getGif());
        this.getChildren().remove(pacman);

        // Game Over
        endingImgs[0].setVisible(true);
        imgsTransition[0].play();
        imgsTransition[0].setOnFinished(event1 -> {
            // Press Enter Text
            endGameTexts[0].setVisible(true);
            txtsFadeTransition[0].play();
            txtsStrokeTransition[0].play();
            // Press ESC Text
            endGameTexts[1].setVisible(true);
            txtsFadeTransition[1].play();
            txtsStrokeTransition[1].play();

            gameScene.setOnKeyPressed(keyEvent -> {
                switch (keyEvent.getCode()) {
                    case ENTER -> {
                        stage.setScene(mainScene);
                        sound.deathSound.stop();
                    }
                    case ESCAPE -> {
                        stage.close();
                    }
                }

            });
        });
    }
    // Setting Ending Texts [Press ESC - Press Enter - You Won] and Their Effects
    private Text[] initializeEndGameTexts(StrokeTransition[] txtsStrokeTransition, FadeTransition[] txtsFadeTransition) {
        Text[] endGameTexts = new Text[3];
        endGameTexts[0] = new Text("Press 'Enter' For Main Menu");
        endGameTexts[0].setLayoutX(240);
        endGameTexts[0].setLayoutY(630);

        endGameTexts[1] = new Text("Press 'ESC' To Exit");
        endGameTexts[1].setLayoutX(291);
        endGameTexts[1].setLayoutY(690);

        endGameTexts[2] = new Text("You Won");
        endGameTexts[2].setLayoutX(306);
        endGameTexts[2].setLayoutY(100);

        for (int i = 0; i < 3; i++) {

            // Setting Text Properties
            endGameTexts[i].setVisible(false);
            endGameTexts[i].setStyle("-fx-background-color: transparent;");
            endGameTexts[i].setFont(Font.font("Arial", FontWeight.BOLD, 24));
            endGameTexts[i].setFill(Color.CORNSILK);
            endGameTexts[i].setStroke(Color.WHITE);
            endGameTexts[i].setStrokeWidth(1);
            // Setting Fading Transition
            txtsFadeTransition[i] = new FadeTransition(Duration.seconds(1), endGameTexts[i]);
            txtsFadeTransition[i].setToValue(0.1);
            txtsFadeTransition[i].setCycleCount(-1);
            txtsFadeTransition[i].setAutoReverse(true);
            // Setting Stroke transition
            txtsStrokeTransition[i] = new StrokeTransition(Duration.seconds(1), endGameTexts[i]);
            txtsStrokeTransition[i].setFromValue(Color.WHITE);
            txtsStrokeTransition[i].setToValue(Color.TRANSPARENT);
            txtsStrokeTransition[i].setAutoReverse(true);
            txtsStrokeTransition[i].setCycleCount(-1);
            // Attaching Txt To The Pane
            this.getChildren().add(endGameTexts[i]);
        }
        // Over Edit For "Your Win" Text.
        endGameTexts[2].setFont(Font.font("Arial", FontWeight.BOLD, 40));
        endGameTexts[2].setFill(Color.GOLD);
        txtsStrokeTransition[2].setFromValue(Color.LIGHTGOLDENRODYELLOW);
        return endGameTexts;
    }

}
