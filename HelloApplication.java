package org.example.gamedemo;


import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private static final int CELL_SIZE = 40;
    private static final int BOARD_WIDTH = 760;
    private static final int BOARD_HEIGHT = 760;
    int level = 1 ;
    int startGame = 0;
    boolean death = false;   // to know pacman is still alive or die
    private static Stage stage;
    @Override
    public void start(Stage stage) {
        // We created this array of 1 element to perform operations on it in Lambda Expression
        // Because it doesn't allow normal variables expressions.
        int[] pacmanGifs = {1};

        // create object to control sound effects
        GameSounds sound = new GameSounds();
        sound.start_sound.play();


        /*----------------------------------------Main Menu-----------------------------------------*/
        Pane mainMenuPane = new Pane();
        Polygon buttonShape1 = new Polygon(0,0,200,0,230,30,30,30);
        Polygon buttonShape2 = new Polygon(30,0,230,0,200,30,0,30);

        Image mainImage = new Image("mainMenuPic.jpg");
        ImageView mainImageView = new ImageView(mainImage);
        mainImageView.setFitWidth(BOARD_WIDTH/1.15);
        mainImageView.setFitHeight(BOARD_HEIGHT/1.25);

        Button []button = new Button[4];

        button[0] = new Button("Play");
        button[1] = new Button("CHARACTERS");
        button[2] = new Button("MAPS");
        button[3] = new Button("INFO");

        for(int i=0 ; i< 4 ; i++){

            button[i].setFont(Font.font("Comic Sans MS",FontWeight.EXTRA_BOLD, FontPosture.ITALIC,20));
            button[i].setTextFill(Color.DARKTURQUOISE);
            button[i].setStyle("-fx-background-color:black ; -fx-border-color:SNOW ;-fx-border-width: 0.7;");
            button[i].setPrefSize(200, 30);
        }

        button[0].setShape(buttonShape1);
        button[1].setShape(buttonShape2);
        button[2].setShape(buttonShape1);
        button[3].setShape(buttonShape2);

        // Create a VBox
        VBox buttonsPane = new VBox();
        buttonsPane.setSpacing(8);
        buttonsPane.getChildren().addAll(button[0],button[1] , button[2] ,button[3]);
        buttonsPane.setLayoutX(39);
        buttonsPane.setLayoutY(352);

        for(Button testBt:button){
            //MouseEntered event
            testBt.setOnMouseEntered(e->{
                testBt.setTextFill(Color.BLACK);
                testBt.setStyle("-fx-background-color:orange ;");
                testBt.setPrefSize(240, 40);
                sound.btnSound.play(); sound.btnSound.stop();
            });
            //MouseExited event
            testBt.setOnMouseExited(e->{
                testBt.setTextFill(Color.DARKTURQUOISE);
                testBt.setStyle("-fx-background-color:black ; -fx-border-color:SNOW ;-fx-border-width: 0.5;");
                testBt.setPrefSize(200, 30);
            });
        }

        mainMenuPane.getChildren().addAll(mainImageView,buttonsPane);
        Scene mainMenuScene = new Scene(mainMenuPane,BOARD_WIDTH/1.15,BOARD_HEIGHT/1.25);

        /*------------------------------Characters Menu-------------------------------*/
        StackPane charactersPane = new StackPane();
        VBox charactersBtnsVbox = new VBox();
        charactersPane.setBackground(new Background(new BackgroundFill(Color.DARKGREY,null,null)));

        ImageView characterBackground = new ImageView(new Image("CharacterBackground.jpg"));
        characterBackground.setFitWidth(665);
        charactersPane.getChildren().add(characterBackground);

        // Adjusting the Pacman ImageView
        ImageView pacmanGif = new ImageView(new Image("PacmanEye.gif"));
        pacmanGif.setFitWidth(200);
        pacmanGif.setFitHeight(200);
        pacmanGif.setTranslateY(20);
        pacmanGif.setTranslateX(120);
        // Adjusting the Pacwoman ImageView
        ImageView pacwomanGif = new ImageView(new Image("pacwoman.gif"));
        pacwomanGif.setFitWidth(200);
        pacwomanGif.setFitHeight(200);
        pacwomanGif.setTranslateY(20);
        pacwomanGif.setTranslateX(120);
        // Adjusting the Pacboy ImageView
        ImageView pacboyGif = new ImageView(new Image("pacboy.gif"));
        pacboyGif.setFitWidth(200);
        pacboyGif.setFitHeight(200);
        pacboyGif.setTranslateY(17);
        pacboyGif.setTranslateX(110);

        // Setting Characters Buttons
        Button[] charactersBtns = new Button[5];
        charactersBtns[0] = new Button("Pacman");
        charactersBtns[1] = new Button("Pacboy");
        charactersBtns[2] = new Button("Pacwoman");
        charactersBtns[3] = new Button("Back");
        charactersBtns[4] = new Button("Choose!");

        for(Button btn: charactersBtns){
            btn.setStyle("-fx-background-color: transparent;");
            btn.setFont(new Font("Comic Sans MS", 30));
            btn.setTextFill(Color.CYAN);
            addButtonEffect(btn, Color.CYAN, Color.MAGENTA);
        }

        for(Button btn: charactersBtns){
            // Mouse Entered Event
            btn.setOnMouseEntered(ev -> {
                sound.btnSound.play(); sound.btnSound.stop();
                btn.setTextFill(Color.MAGENTA);
                btn.setScaleX(1.2);
                btn.setScaleY(1.2);
            });
            // Mouse Exited Events
            btn.setOnMouseExited(ev -> {
                btn.setTextFill(Color.CYAN);
                btn.setScaleX(1);
                btn.setScaleY(1);
            });
        }

        // Setting the Back Button
        charactersBtns[3].setStyle("-fx-background-color: transparent;");
        charactersBtns[3].setFont(new Font("Comic Sans MS", 30));
        charactersBtns[3].setTextFill(Color.DARKRED);
        addButtonEffect(charactersBtns[3], Color.DARKRED, Color.RED);
        charactersBtns[3].setOnMouseMoved(mouseEvent->{sound.btnSound.play(); sound.btnSound.stop();});

        // Setting the Choose Button
        charactersBtns[4].setTextFill(Color.GREEN);
        charactersBtns[4].setTranslateY(1);
        charactersBtns[4].setTranslateX(380);
        addButtonEffect(charactersBtns[4], Color.GREEN, Color.LIGHTGREEN);
        charactersBtns[4].setVisible(false);
        charactersBtns[4].setOnMouseMoved(mouseEvent->{sound.btnSound.play(); sound.btnSound.stop();});


        // Adjusting charactersBtnsVbox (VBox)
        charactersBtnsVbox.setAlignment(Pos.CENTER_LEFT);
        charactersBtnsVbox.setLayoutX(0);
        charactersBtnsVbox.setLayoutY(200);
        charactersBtnsVbox.getChildren().addAll(charactersBtns[0], charactersBtns[2], charactersBtns[1], charactersBtns[3], charactersBtns[4]);
        charactersBtnsVbox.setSpacing(25);
        charactersBtnsVbox.setPadding(new Insets(10, 10, 10, 10));

        charactersPane.getChildren().add(charactersBtnsVbox);
        Scene charactersScene = new Scene(charactersPane, BOARD_WIDTH/1.15, BOARD_HEIGHT/1.25);

        // How we Enter Characters Menu
        button[1].setOnAction(event ->{
            stage.setScene(charactersScene);
        });

        // Handle Menu Buttons Actions
        // Setting Back Button
        charactersBtns[3].setOnAction(event -> {
            stage.setScene(mainMenuScene);
        });
        // Setting Pacman Button
        charactersBtns[0].setOnAction(event -> {
            charactersPane.getChildren().removeAll(pacmanGif, pacwomanGif, pacboyGif);
            charactersPane.getChildren().add(pacmanGif);
            pacmanGifs[0] = 1;
            charactersBtns[4].setVisible(true);
        });
        // Setting Pacboy Button
        charactersBtns[1].setOnAction(event -> {
            charactersPane.getChildren().removeAll(pacmanGif, pacwomanGif, pacboyGif);
            charactersPane.getChildren().add(pacboyGif);
            pacmanGifs[0] = 2;
            charactersBtns[4].setVisible(true);
        });
        // Setting Pacwoman Button
        charactersBtns[2].setOnAction(event -> {
            charactersPane.getChildren().removeAll(pacmanGif, pacwomanGif, pacboyGif);
            charactersPane.getChildren().add(pacwomanGif);
            pacmanGifs[0] = 3;
            charactersBtns[4].setVisible(true);
        });

        // How we Exit Characters Scene (Choose Button)
        charactersBtns[4].setOnAction(event -> {
            stage.setScene(mainMenuScene);
        });
        /*---------------------------End Of Characters Menu---------------------------*/
        /*------------------------------About Menu-------------------------------*/

        /*------------------------------End Of About Menu-------------------------------*/
        /*------------------------------Start (Game)-------------------------------*/
        // How we enter the game (Play Button)
        button[0].setOnAction(event -> {
            Pane gamePane = new Pane();
            MazeView mazeView = new MazeView(new Maze(BOARD_WIDTH,BOARD_HEIGHT,CELL_SIZE, 1));
            Scene gameScene = new Scene(gamePane, BOARD_WIDTH, BOARD_HEIGHT);

            // Setting the Background
            ImageView background = new ImageView(new Image("Background.gif"));
            background.setFitWidth(BOARD_WIDTH);
            background.setFitHeight(BOARD_HEIGHT);
            gamePane.getChildren().add(background);

            // Create Pac-Man, ghosts
            PacMan pacman = new PacMan(1, 1,mazeView, pacmanGifs[0]);
            Ghost[] ghosts = createGhosts(mazeView,level);
            gamePane.getChildren().add(mazeView);
            movePacman(gameScene, pacman);

            stage.setScene(gameScene);

            // Setting Ending Effects (Win - Lose)
            ImageView[] endingImgs = new ImageView[2];
            endingImgs[0] = new ImageView("GameoverScreen.gif");
            endingImgs[1] = new ImageView("WinScreen.jpg");

            ScaleTransition[] imgsTransition = new ScaleTransition[2];

            for(int i = 0; i < 2; i++){
                // Setting Image Properties
                endingImgs[i].setFitHeight(5);
                endingImgs[i].setFitHeight(5);
                endingImgs[i].setPreserveRatio(true);
                endingImgs[i].setLayoutX(380);
                endingImgs[i].setLayoutY(380);
                // Setting Image's Transition Effect
                imgsTransition[i] = new ScaleTransition();
                imgsTransition[i].setNode(endingImgs[i]);
                imgsTransition[i].setDuration(Duration.seconds(.5));
                imgsTransition[i].setToX(153);
                imgsTransition[i].setToY(152);

                gamePane.getChildren().add(endingImgs[i]);
                endingImgs[i].setVisible(false);
            }

            // Game over Prompt Texts.
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

            FadeTransition[] txtsFadeTransition = new FadeTransition[3];
            StrokeTransition[] txtsStrokeTransition = new StrokeTransition[3];

            for(int i = 0; i< 3; i++){

                // Setting Text Properties
                endGameTexts[i].setVisible(false);
                endGameTexts[i].setStyle("-fx-background-color: transparent;");
                endGameTexts[i].setFont( Font.font("Arial", FontWeight.BOLD, 24));
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
                gamePane.getChildren().add(endGameTexts[i]);
            }
            // Over Edit For "Your Win" Text.
            endGameTexts[2].setFont( Font.font("Arial", FontWeight.BOLD, 40));
            endGameTexts[2].setFill(Color.GOLD);
            txtsStrokeTransition[2].setFromValue(Color.LIGHTGOLDENRODYELLOW);
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            Timeline positionChecker = new Timeline(new KeyFrame(Duration.millis(50), e->{
                System.out.println(sound.deathSound.getStatus());
                System.out.println(death);
                for(Ghost ghost : ghosts){
                    // When Pacman Get Caught
                    if(ghost.getCurrentColumn() == pacman.getCurrentColumn() && ghost.getCurrentRow() == pacman.getCurrentRow()) {

                        death = true;
                        pacman.getCountinuous_Motion().stop();
                        pacman.getChildren().remove(pacman.getGif());
                        gamePane.getChildren().remove(pacman);
                        ghost.animation.stop();
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
                                    case ENTER :
                                        stage.setScene(mainMenuScene);
                                        break;
                                    case ESCAPE :
                                        stage.close();
                                        break;
                                }

                            });
                        });
                    }
                }
                //set death sound effects
                if(death){
                    sound.deathSound.play();
                    death = false;
                }else{
                    sound.deathSound.stop();
                }

                // When Pellets == 0
                if(mazeView.getMaze().isFinishedMap())
                {
                    sound.deathSound.play();
                    pacman.getCountinuous_Motion().stop();
                    pacman.getChildren().remove(pacman.getGif());
                    gamePane.getChildren().remove(pacman);
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
                                case ENTER -> stage.setScene(mainMenuScene);
                                case ESCAPE -> stage.close();
                            }

                        });
                    });
                }
            }));
            positionChecker.setCycleCount(-1);
            positionChecker.play();
        });
        /*------------------------------End of Start (Game)-------------------------------*/
        /*----------------------------------End Of Main Menu----------------------------------------*/
        stage.setScene(mainMenuScene);
        stage.setTitle("Pac-Man Game");
        stage.show();
        stage.setResizable(false);
    }
    public void movePacman(Scene scene, PacMan pacman) {
        scene.setOnKeyPressed(event -> {
            switch(event.getCode()){
                case RIGHT :
                    System.out.println("Right");
                    pacman.setlastPress(0);   //to set last press right
                    break;
                case DOWN:
                    System.out.println("Down");
                    pacman.setlastPress(1);   //to set last press down
                    break;
                case UP:
                    System.out.println("Up");
                    pacman.setlastPress(2);    //to set last press up
                    break;
                case LEFT:
                    System.out.println("Left");
                    pacman.setlastPress(3);       //to set last press left
                    break;
            }
        });
        pacman.getCountinuous_Motion().setCycleCount(-1);
        pacman.getCountinuous_Motion().play();
    }
    public Ghost[] createGhosts(MazeView mazeView,int level){
        Ghost[] ghosts = new Ghost[4];
        switch (level){
            case 1,2,3:
                ghosts[0] = new Ghost(17,17,mazeView, level);
                ghosts[0].setMode(1);
                ghosts[1] = new Ghost(17,1,mazeView, level);
                ghosts[1].setMode(2);
                ghosts[2] = new Ghost(1,17,mazeView, level);
                ghosts[2].setMode(3);
                ghosts[3] = new Ghost(9,6,mazeView, level);
                break;

            case 4:
                ghosts[0] = new Ghost(17,17,mazeView, 2);
                ghosts[0].setMode(1);
                ghosts[1] = new Ghost(17,1,mazeView, 3);
                ghosts[1].setMode(2);
                ghosts[2] = new Ghost(1,17,mazeView, 3);
                ghosts[2].setMode(3);
                ghosts[3] = new Ghost(9,6,mazeView, level);
                break;
            case 5:
                ghosts[0] = new Ghost(17,17,mazeView, 3);
                ghosts[0].setMode(1);
                ghosts[1] = new Ghost(17,1,mazeView, 3);
                ghosts[1].setMode(2);
                ghosts[2] = new Ghost(1,17,mazeView, 3);
                ghosts[2].setMode(3);
                ghosts[3] = new Ghost(9,6,mazeView, level);
                break;


        }
        return ghosts;
    }
    public void addButtonEffect(Button button, Color before, Color after){
        button.setOnMouseEntered(event -> {
            button.setTextFill(after);
            button.setScaleX(1.2);
            button.setScaleY(1.2);
        });
        button.setOnMouseExited(event -> {
            button.setTextFill(before);
            button.setScaleX(1);
            button.setScaleY(1);
        });
    }
}
