package com.example.helloapplication;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class HelloApplication extends Application {
    private static final int CELL_SIZE = 40;
    private static final int BOARD_WIDTH = 760;
    private static final int BOARD_HEIGHT = 760;
    int level = 5 ;
    int startGame = 0;
    @Override
    public void start(Stage stage) {
        Pane gamePane = new Pane();
        //game scene
        MazeView mazeView = new MazeView(new Maze(BOARD_WIDTH,BOARD_HEIGHT,CELL_SIZE));
        Scene gameScene = new Scene(gamePane, BOARD_WIDTH, BOARD_HEIGHT);
        // We created this array of 1 element to perform operations on it in Lambda Expression
        // Because it doesn't allow normal variables expressions.
        int[] pacmanGifs = {1};

        // create object to control sound effects
        GameSounds sound = new GameSounds();



        /*----------------------------------------Main Menu-----------------------------------------*/
        BorderPane mainMenuPane = new BorderPane();

        ImageView backgroundGif = new ImageView(new Image("MainMenu.gif"));

        // Create a VBox
        VBox vbox = new VBox();

        // Add buttons to the VBox
        Button startBt = new Button("Start");
        startBt.setStyle("-fx-background-color: transparent;");
        startBt.setFont(new Font("Comic Sans MS", 25));
        startBt.setTextFill(Color.CYAN);

        Button charactersBt = new Button("Characters");
        charactersBt.setStyle("-fx-background-color: transparent;");
        charactersBt.setFont(new Font("Comic Sans MS", 25));
        charactersBt.setTextFill(Color.CYAN);

        Button aboutBt = new Button("About");
        aboutBt.setStyle("-fx-background-color: transparent;");
        aboutBt.setFont(new Font("Comic Sans MS", 25));
        aboutBt.setTextFill(Color.CYAN);

        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);

        vbox.getChildren().addAll(startBt, charactersBt, aboutBt);
        vbox.setAlignment(Pos.CENTER);

        mainMenuPane.setRight(vbox);
        BorderPane.setAlignment(vbox, Pos.CENTER);
        mainMenuPane.setTop(backgroundGif);
        mainMenuPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Scene mainMenuScene = new Scene(mainMenuPane, BOARD_WIDTH/1.15, BOARD_HEIGHT/1.15);

        /*------------------------------Characters Menu-------------------------------*/
        StackPane charactersPane = new StackPane();
        VBox charactersBtns = new VBox();
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


        // Setting the Choose Button
        Button chooseBt = new Button("Choose!");
        chooseBt.setStyle("-fx-background-color: transparent;");
        chooseBt.setFont(new Font("Comic Sans MS", 30));
        chooseBt.setTextFill(Color.GREEN);
        chooseBt.setTranslateY(1);
        chooseBt.setTranslateX(380);
        addButtonEffect(chooseBt, Color.GREEN, Color.LIGHTGREEN);
        chooseBt.setVisible(false);

        // Setting the Pacman Button
        Button pacManBt = new Button("Pacman");
        pacManBt.setStyle("-fx-background-color: transparent;");
        pacManBt.setFont(new Font("Comic Sans MS", 30));
        pacManBt.setTextFill(Color.CYAN);
        addButtonEffect(pacManBt, Color.CYAN, Color.MAGENTA);

        // Setting the Pacwoman Button
        Button pacWomanBt = new Button("Pacwoman");
        pacWomanBt.setStyle("-fx-background-color: transparent;");
        pacWomanBt.setFont(new Font("Comic Sans MS", 30));
        pacWomanBt.setTextFill(Color.CYAN);
        addButtonEffect(pacWomanBt, Color.CYAN, Color.MAGENTA);

        // Setting the Pacboy Button
        Button pacBoyBt = new Button("Pacboy");
        pacBoyBt.setStyle("-fx-background-color: transparent;");
        pacBoyBt.setFont(new Font("Comic Sans MS", 30));
        pacBoyBt.setTextFill(Color.CYAN);
        addButtonEffect(pacBoyBt, Color.CYAN, Color.MAGENTA);
        // Setting the Back Buton
        Button backBt = new Button("Back");
        backBt.setStyle("-fx-background-color: transparent;");
        backBt.setFont(new Font("Comic Sans MS", 30));
        backBt.setTextFill(Color.DARKRED);
        addButtonEffect(backBt, Color.DARKRED, Color.RED);

        // Adjusting charactersBtns VBox
        charactersBtns.setAlignment(Pos.CENTER_LEFT);
        charactersBtns.setLayoutX(0);
        charactersBtns.setLayoutY(200);
        charactersBtns.getChildren().addAll(pacManBt, pacWomanBt, pacBoyBt, backBt, chooseBt);
        charactersBtns.setSpacing(25);
        charactersBtns.setPadding(new Insets(10, 10, 10, 10));


        charactersPane.getChildren().add(charactersBtns);
        Scene charactersScene = new Scene(charactersPane, BOARD_WIDTH/1.15, BOARD_HEIGHT/1.25);

        // How we Enter Characters Menu
        charactersBt.setOnAction(event ->{
            stage.setScene(charactersScene);
        });

        // Handle Menu Buttons Actions
        backBt.setOnAction(event -> {
            stage.setScene(mainMenuScene);
        });
        pacManBt.setOnAction(event -> {
            charactersPane.getChildren().add(pacmanGif);
            charactersPane.getChildren().removeAll(pacboyGif, pacwomanGif);
            pacmanGifs[0] = 1;
            chooseBt.setVisible(true);
        });
        pacBoyBt.setOnAction(event -> {
            charactersPane.getChildren().add(pacboyGif);
            charactersPane.getChildren().removeAll(pacmanGif, pacwomanGif);
            pacmanGifs[0] = 2;
            chooseBt.setVisible(true);
        });
        pacWomanBt.setOnAction(event -> {
            charactersPane.getChildren().add(pacwomanGif);
            charactersPane.getChildren().removeAll(pacboyGif, pacmanGif);
            pacmanGifs[0] = 3;
            chooseBt.setVisible(true);
        });

        // How we Exit Characters Scene
        chooseBt.setOnAction(event -> {
            stage.setScene(mainMenuScene);
        });
        /*-----------------------------------------------------------------------*/

        startBt.setOnAction(event -> {


            // Setting the Background
            ImageView background = new ImageView(new Image("Background.gif"));
            background.setFitWidth(BOARD_WIDTH);
            background.setFitHeight(BOARD_HEIGHT);
            gamePane.getChildren().add(background);

            // Create Pac-Man, ghosts
            PacMan pacman = new PacMan(1, 1,mazeView, pacmanGifs[0]);
            Ghost[] ghosts = createGhosts(mazeView,level);
            gamePane.getChildren().add(mazeView);
            stage.setScene(gameScene);
            movePacman(gameScene, pacman);

            Timeline positionChecker = new Timeline(new KeyFrame(Duration.millis(50), e->{

                for(Ghost ghost : ghosts)
                    if(ghost.getCurrentColumn() == pacman.getCurrentColumn() && ghost.getCurrentRow() == pacman.getCurrentRow()){

                        sound.deathSound.play();
                        pacman.getCountinuous_Motion().stop();
                        pacman.getChildren().remove(pacman.getGif());
                        gamePane.getChildren().remove(pacman);
                        ghost.animation.stop();
            }}));
            positionChecker.setCycleCount(-1);
            positionChecker.play();

        });

        // Set btns sound effects

        charactersBt.setOnMouseMoved(mouseEvent->{sound.btnSound.play(); sound.btnSound.stop();});
        backBt.setOnMouseMoved(mouseEvent->{sound.btnSound.play(); sound.btnSound.stop();});
        pacBoyBt.setOnMouseMoved(mouseEvent->{sound.btnSound.play(); sound.btnSound.stop();});
        pacWomanBt.setOnMouseMoved(mouseEvent->{sound.btnSound.play(); sound.btnSound.stop();});
        chooseBt.setOnMouseMoved(mouseEvent->{sound.btnSound.play(); sound.btnSound.stop();});
        startBt.setOnMouseMoved(mouseEvent->{sound.btnSound.play(); sound.btnSound.stop();});
        aboutBt.setOnMouseMoved(mouseEvent->{sound.btnSound.play(); sound.btnSound.stop();});


        // Add sound to start menu
        mainMenuPane.getChildren().add(sound);
        sound.start_sound.play();


        /*------------------------------------------------------------------------------------------*/
        stage.setScene(mainMenuScene);
        stage.setTitle("Pac-Man Game");
        stage.show();


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
