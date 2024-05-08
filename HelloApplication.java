package com.example.pac_man;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {
    private static final int CELL_SIZE = 40;
    private static final int BOARD_WIDTH = 760;
    private static final int BOARD_HEIGHT = 760;
    int level = 1 ;
    boolean death = false;   // to know pacman is still alive or die
    boolean win = false;      // to know if user win
    private Timeline positionChecker;
    private Ghost[] ghosts;
    private PacMan pacman;
    private MazeView mazeView;
    private GameSounds sound;
    private static Stage stage;
    private Scene mainScene;
    private Scene gameScene;
    private Scene charactersScene ;
    private Scene mapsScene ;
    private Scene infoScene ;
    private int mapNumber = 1;
    private Button []mainMenueBtns ;
    int pacmanGifNum = 1;
    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;

        // create object to control sound effects
        sound = new GameSounds();
        sound.start_sound.play();

        //main menu
        setMainScene();

        //Characters Menu
        setCharactersScene();

        //INFO scene
        setInfoScene();

        //maps menu
        setMapScene();

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Pac-Man Game");
        primaryStage.getIcons().add(new Image("gameIcon.jpg"));
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    private void startGameScene() {
        Pane gamePane = createGamePane();
        ImageView background = createBackground("Background.gif" , BOARD_WIDTH,BOARD_HEIGHT);
        gamePane.getChildren().addAll(background, mazeView);

        stage.setScene(gameScene);

        movePacman(gameScene, pacman);

        checkLife(gamePane, pacman, ghosts);
    }
    private Pane createGamePane() {
        Pane gamePane = new Pane();
        gameScene = new Scene(gamePane, BOARD_WIDTH, BOARD_HEIGHT);
        sound.start_sound.stop();
        mazeView = new MazeView(new Maze(BOARD_WIDTH, BOARD_HEIGHT, CELL_SIZE, mapNumber));
        pacman = new PacMan(1, 1, mazeView, pacmanGifNum);
        ghosts = createGhosts(mazeView, level);
        return gamePane;
    }
    private ImageView createBackground(String url , double width, double height ) {
        ImageView background = new ImageView(url);
        background.setFitWidth(width);
        background.setFitHeight(height);
        return background;
    }
    private void checkLife(Pane gamePane, PacMan pacman, Ghost[] ghosts) {
        death = false;
        win = false;
        sound.deathSound.stop();
        sound.winSound.stop();
        positionChecker = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            for (Ghost ghost : ghosts) {
                if (ghost.getCurrentColumn() == pacman.getCurrentColumn() && ghost.getCurrentRow() == pacman.getCurrentRow()) {
                    death = true;
                    ghost.ghostMovement.stop();
                }
            }
            if (death || pacman.getMazeView().getMaze().isFinishedMap()) {
                startEndScene(gamePane, death);
            }
        }));
        positionChecker.setCycleCount(-1);
        positionChecker.play();
    }
    private void startEndScene(Pane gamePane, boolean isDeath) {
        positionChecker.stop();

        ImageView[] endingImgs = {new ImageView("GameoverScreen.gif"), new ImageView("WinScreen.jpg")};
        ScaleTransition[] imgsTransition = new ScaleTransition[2];

        for(int i = 0; i < 2; i++){
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
            imgsTransition[i].setToY(152);

            gamePane.getChildren().add(endingImgs[i]);
            endingImgs[i].setVisible(false);
        }

        FadeTransition[] txtsFadeTransition  = new FadeTransition[3];
        StrokeTransition[] txtsStrokeTransition = new StrokeTransition[3];
        Text[] endGameTexts = initializeEndGameTexts(gamePane,txtsStrokeTransition,txtsFadeTransition);

        if (isDeath) {
            handleGameOver(gamePane, endingImgs, endGameTexts, txtsFadeTransition,txtsStrokeTransition, imgsTransition);
        } else {
            handleGameWin(gamePane, endingImgs, endGameTexts, txtsFadeTransition,txtsStrokeTransition, imgsTransition);
        }
    }
    private void handleGameWin(Pane gamePane, ImageView[] endingImgs, Text[] endGameTexts, FadeTransition[] txtsFadeTransition,
                               StrokeTransition[] txtsStrokeTransition, ScaleTransition[] imgsTransition ) {
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
                    case ENTER -> stage.setScene(mainScene);
                    case ESCAPE -> stage.close();
                }

            });
        });
        positionChecker.stop();
        sound.winSound.play();
        win = false;
    }
    private void handleGameOver(Pane gamePane, ImageView[] endingImgs, Text[] endGameTexts, FadeTransition[] txtsFadeTransition,
                               StrokeTransition[] txtsStrokeTransition, ScaleTransition[] imgsTransition ) {
        sound.deathSound.play();
        death = false;

        pacman.getCountinuous_Motion().stop();
        pacman.getChildren().remove(pacman.getGif());
        gamePane.getChildren().remove(pacman);

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
                        stage.setScene(mainScene);
                        break;
                    case ESCAPE :
                        stage.close();
                        break;
                }

            });
        });
    }
    private Text[] initializeEndGameTexts(Pane gamePane,StrokeTransition[] txtsStrokeTransition,  FadeTransition[] txtsFadeTransition ) {
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
        return endGameTexts;
    }
    private void setMapScene() {

        //maps images, pane for them
        ImageView[] maps  = {new ImageView("map1.png"),new ImageView("map2.png"),new ImageView("map3.png")};
        StackPane mapsImagesPane = new StackPane(maps[0],maps[1],maps[2]);
        mapsImagesPane.setLayoutX(300);
        mapsImagesPane.setLayoutY(150);
        mapsImagesPane.setBorder(Border.stroke(Color.TRANSPARENT));
        mapsImagesPane.setBackground(Background.fill(Color.TRANSPARENT));

        //maps styling
        for(int i=0;i<3;i++){
            maps[i].setFitHeight(280);
            maps[i].setFitWidth(280);
            maps[i].setVisible(false);
        }


        //set buttons and box for them
        VBox mapsButtonsPane = new VBox(30);
        mapsButtonsPane.setAlignment(Pos.CENTER);
        mapsButtonsPane.setLayoutX(50);
        mapsButtonsPane.setLayoutY(100);

        Button[] mapsButtons = new Button[4];
        for(int i=0;i<4;i++){
            mapsButtons[i] = new Button("MAP "+(i+1));
            mapsButtons[i].setStyle("-fx-background-color: transparent;");
            mapsButtons[i].setFont(Font.font("Comic Sans MS", 35));
            mapsButtons[i].setTextFill(Color.rgb(160,160,170));

            //buttons' events
            if(i==3){
                mapsButtons[i].setText("BACK");
                addButtonEffect(mapsButtons[3] , Color.rgb(160,160,170),Color.rgb(229,187,229));
                mapsButtons[3].setOnAction(e->{ stage.setScene(mainScene);
                    if(sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING ){
                        sound.btnSound.stop();
                        sound.btnSound.play();
                    }else{
                        sound.btnSound.play();
                    }
                });
            }
            else {
                int finalI = i;
                mapsButtons[i].setOnAction(e->{
                    mapNumber = finalI +1;
                    if(sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING ){
                        sound.btnSound.stop();
                        sound.btnSound.play();
                    }else{
                        sound.btnSound.play();
                    }
                    stage.setScene(mainScene);
                });
                mapsButtons[i].setOnMouseEntered(e->{
                    mapsButtons[finalI].setTextFill(Color.rgb(229,187,229));
                    mapsButtons[finalI].setScaleX(1.2);
                    mapsButtons[finalI].setScaleY(1.2);
                    maps[finalI].setVisible(true);
                    if(sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING ){
                        sound.btnSound.stop();
                        sound.btnSound.play();
                    }else{
                        sound.btnSound.play();
                    }

                });
                mapsButtons[i].setOnMouseExited(e->{
                    mapsButtons[finalI].setTextFill(Color.rgb(160,160,170));
                    mapsButtons[finalI].setScaleX(1);
                    mapsButtons[finalI].setScaleY(1);
                    maps[finalI].setVisible(false);
                });
            }

            mapsButtonsPane.getChildren().add(mapsButtons[i]);
        }

        //maps scene
        ImageView mapsBackground = createBackground("mapsBackground.jpg" ,BOARD_WIDTH/1.15,BOARD_HEIGHT/1.25 );
        Pane mapsPane = new Pane(mapsBackground,mapsButtonsPane,mapsImagesPane);
        mapsScene = new Scene(mapsPane,BOARD_WIDTH/1.15,BOARD_HEIGHT/1.25);



    }
    public void setMainScene(){

        //set background
        ImageView mainImageView = createBackground("mainMenuPic.jpg",BOARD_WIDTH/1.15 ,BOARD_HEIGHT/1.25);

        //set the buttons
        Pane mainMenuPane = new Pane();
        Pane btnsPane = mainMenuBtnsPane();
        mainMenuPane.getChildren().addAll(mainImageView,btnsPane);

        //set the main menu scene
        mainScene = new Scene(mainMenuPane,BOARD_WIDTH/1.15,BOARD_HEIGHT/1.25);

    }
    public void setCharactersScene(){

        //pane for characters' gifs
        StackPane charactersPane = charactersGifsPane();

        //pane for buttons to choose a character gif
        VBox charactersBtnsVbox = charactersBtnsPane(charactersPane);

        //set main pane , add buttons' pane and characters' pane to it
        Pane charactersMenuPane = new Pane();
        ImageView characterBackground = createBackground("CharacterBackground.jpg",BOARD_WIDTH/1.15,BOARD_HEIGHT/1.25);
        charactersMenuPane.getChildren().addAll(characterBackground,charactersBtnsVbox ,charactersPane);

        //create the scene and add the main pane to it
        charactersScene = new Scene(charactersMenuPane, BOARD_WIDTH/1.15, BOARD_HEIGHT/1.25);

    }
    private void setInfoScene( ) {
        Text txt1 = new Text("this mission has done.");
        txt1.setFont(Font.font(30));
        txt1.setFill(Color.rgb(125,154,169));
        txt1.setStyle("-fx-font-family: 'Copperplate Gothic Bold'");
        txt1.setLayoutX(20);
        txt1.setLayoutY(35);

        Button backBtn = new Button("Back");
        backBtn.setBackground(Background.EMPTY);
        backBtn.setLayoutX(410);
        backBtn.setLayoutY(600);
        backBtn.setFont(Font.font(35));
        backBtn.setTextFill(Color.DARKMAGENTA);
        backBtn.setOnAction(e-> stage.setScene(mainScene));
        addButtonEffect(backBtn , Color.DARKMAGENTA , Color.rgb(139,139,139));



        ImageView infoBackground = createBackground("infoBackground.jpg" ,1000 ,700);
        Pane pane = new Pane(infoBackground,txt1,backBtn);

        infoScene = new Scene(pane,1000,700);
    }
    public void movePacman(Scene gameScene, PacMan pacman) {
        gameScene.setOnKeyPressed(event -> {
            switch(event.getCode()){
                case RIGHT :
                    pacman.setlastPress(0);   //to set last press right
                    break;
                case DOWN:
                    pacman.setlastPress(1);   //to set last press down
                    break;
                case UP:
                    pacman.setlastPress(2);    //to set last press up
                    break;
                case LEFT:
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
            if(sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING ){
                sound.btnSound.stop();
                sound.btnSound.play();
            }else{
                sound.btnSound.play();
            }
        });
        button.setOnMouseExited(event -> {
            button.setTextFill(before);
            button.setScaleX(1);
            button.setScaleY(1);
        });
    }
    private Pane mainMenuBtnsPane(){
        Polygon buttonShape1 = new Polygon(0,0,200,0,230,30,30,30);
        Polygon buttonShape2 = new Polygon(30,0,230,0,200,30,0,30);

        //set the buttons' array, Vbox for them
        VBox buttonsPane = new VBox(8);
        buttonsPane.setLayoutX(39);
        buttonsPane.setLayoutY(352);

        mainMenueBtns = new Button[4];
        String[] btnsText = {"Play","CHARACTERS","MAPS","INFO"};

        for(int i=0 ; i< 4 ; i++){
            //styling
            mainMenueBtns[i] = new Button(btnsText[i]);
            mainMenueBtns[i].setFont(Font.font("Comic Sans MS",FontWeight.EXTRA_BOLD, FontPosture.ITALIC,20));
            mainMenueBtns[i].setStyle("-fx-background-color:black ; -fx-border-color:#a0a0aa ;-fx-border-width: 0.7;-fx-text-fill: DARKTURQUOISE");
            mainMenueBtns[i].setPrefSize(200, 30);
            mainMenueBtns[i].setShape(i%2 == 0?  buttonShape1 : buttonShape2 );

            buttonsPane.getChildren().add(mainMenueBtns[i]);

            //styling events
            int finalI = i;
            mainMenueBtns[i].setOnMouseEntered(e->{
                mainMenueBtns[finalI].setTextFill(Color.BLACK);
                mainMenueBtns[finalI].setStyle("-fx-background-color:orange ;");
                mainMenueBtns[finalI].setPrefSize(240, 40);
                if(sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING ){
                    sound.btnSound.stop();
                    sound.btnSound.play();
                }else{
                    sound.btnSound.play();
                }
            });
            mainMenueBtns[i].setOnMouseExited(e->{
                mainMenueBtns[finalI].setTextFill(Color.DARKTURQUOISE);
                mainMenueBtns[finalI].setStyle("-fx-background-color:black ; -fx-border-color:SNOW ;-fx-border-width: 0.5;");
                mainMenueBtns[finalI].setPrefSize(200, 30);
            });

        }

        // How we enter the game (Play Button)
        mainMenueBtns[0].setOnAction(event -> startGameScene());
        mainMenueBtns[1].setOnAction(e-> stage.setScene(charactersScene) );
        mainMenueBtns[2].setOnAction(e-> stage.setScene(mapsScene) );
        mainMenueBtns[3].setOnAction(e-> stage.setScene(infoScene) );
        return buttonsPane;
    }
    private VBox charactersBtnsPane(StackPane charactersPane){
        VBox charactersBtnsVbox = new VBox(25);
        charactersBtnsVbox.setAlignment(Pos.CENTER_LEFT);
        charactersBtnsVbox.setLayoutX(0);
        charactersBtnsVbox.setLayoutY(200);
        charactersBtnsVbox.setPadding(new Insets(10));


        // Setting Characters Buttons
        Button[] charactersBtns = new Button[4];
        String[] btnsText = {"Pacman","Pacboy","Pacwoman","Back"};
        for(int i=0;i<3; i++){
            //set button style
            charactersBtns[i] = new Button(btnsText[i]);
            charactersBtns[i].setStyle("-fx-background-color: transparent;");
            charactersBtns[i].setFont(new Font("Comic Sans MS", 30));
            charactersBtns[i].setTextFill(Color.CYAN);

            //set the button event
            int finalI = i;
            charactersBtns[i].setOnAction(event -> {
                pacmanGifNum = finalI+1;
                stage.setScene(mainScene);
            });
            charactersBtns[i].setOnMouseEntered(event -> {
                charactersPane.getChildren().get(finalI).setVisible(true);
                charactersBtns[finalI].setTextFill(Color.MAGENTA);
                charactersBtns[finalI].setScaleX(1.2);
                charactersBtns[finalI].setScaleY(1.2);
                if(sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING ){
                    sound.btnSound.stop();
                    sound.btnSound.play();
                }else{
                    sound.btnSound.play();
                }
            });
            charactersBtns[i].setOnMouseExited(event -> {
                charactersPane.getChildren().get(finalI).setVisible(false);
                charactersBtns[finalI].setTextFill(Color.CYAN);
                charactersBtns[finalI].setScaleX(1);
                charactersBtns[finalI].setScaleY(1);
            });

            //add the button to the vbox
            charactersBtnsVbox.getChildren().add(charactersBtns[i]);
        }

        // Setting the Back Button
        charactersBtns[3] = new Button(btnsText[3]);
        charactersBtns[3].setFont(Font.font("Comic Sans MS", 30));
        charactersBtns[3].setBackground(Background.fill(Color.TRANSPARENT));
        charactersBtns[3].setTextFill(Color.DARKRED);
        addButtonEffect(charactersBtns[3], Color.DARKRED, Color.RED);
        charactersBtns[3].setOnAction(event -> stage.setScene(mainScene) );
        charactersBtnsVbox.getChildren().add(charactersBtns[3]);
        return charactersBtnsVbox;
    }
    private StackPane charactersGifsPane(){
        //pane for characters' gifs
        StackPane charactersPane = new StackPane();
        charactersPane.setBackground(Background.fill(Color.TRANSPARENT));
        charactersPane.setLayoutX(120);
        charactersPane.setLayoutY(120);

        // Adjusting characters pane (VBox)
        VBox charactersBtnsVbox = new VBox(25);
        charactersBtnsVbox.setAlignment(Pos.CENTER_LEFT);
        charactersBtnsVbox.setLayoutX(10);
        charactersBtnsVbox.setLayoutY(150);
        charactersBtnsVbox.setPadding(new Insets(10));

        //set the gif for every character
        String[] gifsUrls = { "PacmanEye.gif","pacwoman.gif","pacboy.gif"};
        ImageView[] charactersGifs = new ImageView[3];
        for(int i=0;i<3;i++){
            charactersGifs[i] = new ImageView(gifsUrls[i]);
            charactersGifs[i].setFitWidth(150);
            charactersGifs[i].setFitHeight(150);
            charactersGifs[i].setTranslateY(160);
            charactersGifs[i].setTranslateX(130);
            charactersGifs[i].setVisible(false);
            charactersPane.getChildren().add(charactersGifs[i]);
        }

        return charactersPane;
    }
}
