package org.example.gamedemo;

import javafx.animation.*;
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
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class HelloApplication extends Application {
    private static final int CELL_SIZE = 40;
    private static final int BOARD_WIDTH = 760;
    private static final int BOARD_HEIGHT = 760;
    int level = 1;
    private Ghost[] ghosts;
    private PacMan pacman;
    private MazeView mazeView;
    private GameSounds sound;
    private MediaPlayer introPlayer;
    private static Stage stage;
    private Scene mainScene;
    private Scene charactersScene;
    private Scene mapsScene;
    private Scene infoScene;
    private Scene levelScene;
    private boolean introFinished = false;
    private int mapNumber = 1;
    private Button[] mainMenueBtns;
    int pacmanGifNum = 1;
    GamePane gamepane;

    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;

        // create object to control sound effects
        sound = new GameSounds();

        //Main Menu
        setMainScene();

        //Characters Menu
        setCharactersScene();

        //Info Scene
        setInfoScene();

        //Level Scene
        setLevelScene();

        //Maps Menu
        setMapScene();

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Pac-Man Game");
        primaryStage.getIcons().add(new Image("GameIcon.jpg"));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /*--------------------------Establishing Scenes-----------------------*/

    // Establishing the Scene of Main Menu
    public void setMainScene() {
        Pane mainMenuPane = new Pane();

        //set the main menu scene
        mainScene = new Scene(mainMenuPane, BOARD_WIDTH / 1.15, BOARD_HEIGHT / 1.25);

        // Set Intro Video
        introPlayer = new MediaPlayer(new Media(new File("D:\\Resources\\Sounds\\Intro.mp4").toURI().toString()));
        MediaView introView = new MediaView(introPlayer);
        introView.setFitWidth(BOARD_WIDTH/ 1.14);
        introView.setFitHeight(BOARD_HEIGHT/ 1.1); // Trial & Error Value
        if(introPlayer.getStatus() == MediaPlayer.Status.PLAYING ){
            introPlayer.stop();
            introPlayer.play();
        }else{
            introPlayer.play();
        }
        String[] introStrs = {"CSE-27-JavaNewbies", "Present", "Press \"Enter\" To Skip"};
        Text[] introTxts = new Text[3];
        FadeTransition[] txtFd = new FadeTransition[3];
        StrokeTransition[] txtSt = new StrokeTransition[3];

        for(int i = 0; i < 3; i++){
            introTxts[i] = new Text(introStrs[i]);
            introTxts[i].setFont(Font.font("Garamond", FontWeight.NORMAL, FontPosture.REGULAR, 30));
            introTxts[i].setFill(Color.WHITE);
            introTxts[i].setOpacity(0);
            txtFd[i] = new FadeTransition(Duration.seconds(2), introTxts[i]);
            txtFd[i].setFromValue(0);
            txtFd[i].setToValue(1);
            txtFd[i].setCycleCount(1);
            txtSt[i] = new StrokeTransition(Duration.seconds(2), introTxts[i]);
            txtSt[i].setFromValue(Color.TRANSPARENT);
            txtSt[i].setToValue(Color.web("#c0c0c0"));
            txtSt[i].setCycleCount(-1);
            txtSt[i].setAutoReverse(true);
        }
        introTxts[0].setX(205);
        introTxts[0].setY(50);
        introTxts[1].setX(290);
        introTxts[1].setY(110);
        introTxts[2].setX(195);
        introTxts[2].setY(565);
        mainMenuPane.getChildren().addAll(introView, introTxts[0], introTxts[1], introTxts[2]);

        Timeline txtTl = new Timeline(new KeyFrame(Duration.seconds(6), event ->{
            for (int i = 0; i < 3; i++){
                txtFd[i].play();
                txtSt[i].play();
            }
        }));
        txtTl.setCycleCount(1);
        txtTl.play();

        Timeline introTl = new Timeline(new KeyFrame(Duration.seconds(27), event ->{
            for(int i = 0; i < 3; i++){
                txtFd[i].stop();
                txtSt[i].stop();
            }
            introPlayer.stop();
            mainMenuPane.getChildren().clear();
            introFinished = true;
        }));
        introTl.setOnFinished(event -> {

            // Set Background
            ImageView mainImageView = new ImageView("mainMenuPic.jpg");
            mainImageView.setFitWidth(BOARD_WIDTH / 1.14);
            mainImageView.setFitHeight(BOARD_HEIGHT / 1.25);

            // Set The Panes
            Pane btnsPane = mainMenuBtnsPane();
            Pane palestinePane = palestinePane();
            mainMenuPane.getChildren().addAll(mainImageView, btnsPane, palestinePane);
        });
        introTl.setCycleCount(1);
        introTl.play();

        mainScene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER -> {
                    if(!introFinished){
                        for(int i = 0; i < 3; i++){
                            txtFd[i].stop();
                            txtSt[i].stop();
                        }
                        introPlayer.stop();
                        mainMenuPane.getChildren().clear();

                        // Set Background
                        ImageView mainImageView = new ImageView("mainMenuPic.jpg");
                        mainImageView.setFitWidth(BOARD_WIDTH / 1.14);
                        mainImageView.setFitHeight(BOARD_HEIGHT / 1.25);

                        // Set The Panes
                        Pane btnsPane = mainMenuBtnsPane();
                        Pane palestinePane = palestinePane();
                        mainMenuPane.getChildren().addAll(mainImageView, btnsPane, palestinePane);

                        introFinished = true;
                    }
                }
            }
        });
    }

    /*----------Main Menu Establishing Methods----------*/
    // Returning main menu buttons' pane [Main Menu]
    private Pane mainMenuBtnsPane() {
        Polygon buttonShape1 = new Polygon(0, 0, 200, 0, 230, 30, 30, 30);
        Polygon buttonShape2 = new Polygon(30, 0, 230, 0, 200, 30, 0, 30);

        //set the buttons' array, Vbox for them
        VBox buttonsPane = new VBox(8);
        buttonsPane.setLayoutX(39);
        buttonsPane.setLayoutY(352);

        mainMenueBtns = new Button[5];
        String[] btnsText = {"Play", "CHARACTERS", "MAPS", "LEVEL", "INFO"};

        for (int i = 0; i < 5; i++) {
            //styling
            mainMenueBtns[i] = new Button(btnsText[i]);
            mainMenueBtns[i].setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 20));
            mainMenueBtns[i].setStyle("-fx-background-color:black ; -fx-border-color:#a0a0aa ;-fx-border-width: 0.7;");
            mainMenueBtns[i].setTextFill(Color.DARKTURQUOISE);
            mainMenueBtns[i].setPrefSize(200, 30);
            mainMenueBtns[i].setShape(i % 2 == 0 ? buttonShape1 : buttonShape2);


            buttonsPane.getChildren().add(mainMenueBtns[i]);

            //styling events
            int finalI = i;
            mainMenueBtns[i].setOnMouseEntered(e -> {
                mainMenueBtns[finalI].setTextFill(Color.BLACK);
                mainMenueBtns[finalI].setStyle("-fx-background-color:orange ;");
                mainMenueBtns[finalI].setPrefSize(240, 40);
                if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                    sound.btnSound.stop();
                    sound.btnSound.play();
                } else {
                    sound.btnSound.play();
                }
            });
            mainMenueBtns[i].setOnMouseExited(e -> {
                mainMenueBtns[finalI].setTextFill(Color.DARKTURQUOISE);
                mainMenueBtns[finalI].setStyle("-fx-background-color:black; -fx-border-color:snow; -fx-border-width: 0.5;");
                mainMenueBtns[finalI].setPrefSize(200, 30);
            });


        }

        // Navigation
        mainMenueBtns[0].setOnAction(e -> {
            System.gc();
            stage.setScene(new GamePane(mapNumber,sound,level,pacmanGifNum,stage,mainScene).getScene());
            sound.start_sound.stop();
        });
        mainMenueBtns[1].setOnAction(e -> {
            stage.setScene(charactersScene);
            sound.start_sound.stop();
        });
        mainMenueBtns[2].setOnAction(e -> {
            stage.setScene(mapsScene);
            sound.start_sound.stop();
        });
        mainMenueBtns[3].setOnAction(e -> {
            stage.setScene(levelScene);
            sound.start_sound.stop();
        });
        mainMenueBtns[4].setOnAction(e -> {
            stage.setScene(infoScene);
            sound.start_sound.stop();
        });
        return buttonsPane;
    }
    private Pane palestinePane(){
        HBox palestinePane = new HBox();

        // Set Palestine Flag
        ImageView[] caseView = {new ImageView("TheCase.png"), new ImageView("TheCase.png")};
        for(int i = 0; i < 2; i++){
            caseView[i].setFitWidth(60);
            caseView[i].setFitHeight(60);
        }
        // Set Case Text
        Text caseTxt = new Text("We Live For\n The CASE.");
        caseTxt.setFont(Font.font("Garamond", 20));
        caseTxt.setFill(Color.DARKRED);
        caseTxt.setStroke(Color.RED);
        caseTxt.setStrokeWidth(.7);

        palestinePane.getChildren().addAll(caseView[0] ,caseTxt, caseView[1]);
        palestinePane.setLayoutX(222);
        palestinePane.setLayoutY(0);

        return palestinePane;
    }

    /*---------------------------------------------------*/

    // Establishing the Scene of Characters (Switched by Characters Bt)
    public void setCharactersScene() {

        //pane for characters' gifs
        StackPane charactersPane = charactersGifsPane();

        //pane for buttons to choose a character gif
        VBox charactersBtnsVbox = charactersBtnsPane(charactersPane);

        //set main pane , add buttons' pane and characters' pane to it
        Pane charactersMenuPane = new Pane();
        ImageView characterBackground = new ImageView("CharacterBackground.jpg");
        characterBackground.setFitWidth(BOARD_WIDTH / 1.15);
        characterBackground.setFitHeight(BOARD_HEIGHT / 1.25);
        charactersMenuPane.getChildren().addAll(characterBackground, charactersBtnsVbox, charactersPane);

        //create the scene and add the main pane to it
        charactersScene = new Scene(charactersMenuPane, BOARD_WIDTH / 1.15, BOARD_HEIGHT / 1.25);

    }

    /*-----------Character Establishing Methods-----------*/
    // Returning Vbox of characters' buttons at [Character Scene]
    private VBox charactersBtnsPane(StackPane charactersPane) {
        VBox charactersBtnsVbox = new VBox(25);
        charactersBtnsVbox.setAlignment(Pos.CENTER_LEFT);
        charactersBtnsVbox.setLayoutX(0);
        charactersBtnsVbox.setLayoutY(200);
        charactersBtnsVbox.setPadding(new Insets(10));


        // Setting Characters Buttons
        Button[] charactersBtns = new Button[4];
        String[] btnsText = {"Pacman", "Pacboy", "Pacwoman", "Back"};
        for (int i = 0; i < 3; i++) {
            //set button style
            charactersBtns[i] = new Button(btnsText[i]);
            charactersBtns[i].setStyle("-fx-background-color: transparent;");
            charactersBtns[i].setFont(new Font("Comic Sans MS", 30));
            charactersBtns[i].setTextFill(Color.CYAN);


            //set the button event
            int finalI = i;
            charactersBtns[i].setOnAction(event -> {
                sound.start_sound.stop();
                pacmanGifNum = finalI + 1;
                stage.setScene(mainScene);
            });
            charactersBtns[i].setOnMouseEntered(event -> {
                charactersPane.getChildren().get(finalI).setVisible(true);
                charactersBtns[finalI].setTextFill(Color.MAGENTA);
                charactersBtns[finalI].setScaleX(1.2);
                charactersBtns[finalI].setScaleY(1.2);
                if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                    sound.btnSound.stop();
                    sound.btnSound.play();
                } else {
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
        charactersBtns[3].setBackground(Background.EMPTY);
        charactersBtns[3].setTextFill(Color.DARKRED);
        addButtonEffect(charactersBtns[3], Color.DARKRED, Color.RED);
        charactersBtns[3].setOnAction(event -> stage.setScene(mainScene));
        charactersBtns[3].setOnMouseEntered(e -> {
            sound.start_sound.stop();
            if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                sound.btnSound.stop();
                sound.btnSound.play();
            } else {
                sound.btnSound.play();
            }
        });
        charactersBtnsVbox.getChildren().add(charactersBtns[3]);
        return charactersBtnsVbox;
    }

    // Returning pane of characters' gifs at [Character Scene]
    private StackPane charactersGifsPane() {
        //pane for characters' gifs
        StackPane charactersPane = new StackPane();
        charactersPane.setBackground(Background.EMPTY);
        charactersPane.setLayoutX(120);
        charactersPane.setLayoutY(120);

        // Adjusting characters pane (VBox)
        VBox charactersBtnsVbox = new VBox(25);
        charactersBtnsVbox.setAlignment(Pos.CENTER_LEFT);
        charactersBtnsVbox.setLayoutX(10);
        charactersBtnsVbox.setLayoutY(150);
        charactersBtnsVbox.setPadding(new Insets(10));

        //set the gif for every character
        String[] gifsUrls = {"PacmanEye.gif", "pacwoman.gif", "pacboy.gif"};
        ImageView[] charactersGifs = new ImageView[3];
        for (int i = 0; i < 3; i++) {
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
    /*---------------------------------------------------*/

    // Establishing the Scene of Maps (Switched by Maps Bt)
    private void setMapScene() {

        //maps images, pane for them
        ImageView[] maps = {new ImageView("map1.png"), new ImageView("map2.png"), new ImageView("map3.png")};
        StackPane mapsImagesPane = new StackPane(maps[0], maps[1], maps[2]);
        mapsImagesPane.setLayoutX(300);
        mapsImagesPane.setLayoutY(150);
        // mapsImagesPane.setBorder(Border.stroke(Color.TRANSPARENT));
        mapsImagesPane.setBackground(Background.EMPTY);

        //maps styling
        for (int i = 0; i < 3; i++) {
            maps[i].setFitHeight(280);
            maps[i].setFitWidth(280);
            maps[i].setVisible(false);
        }


        //ste buttons and box for them
        VBox mapsButtonsPane = new VBox(30);
        mapsButtonsPane.setAlignment(Pos.CENTER);
        mapsButtonsPane.setLayoutX(50);
        mapsButtonsPane.setLayoutY(100);

        Button[] mapsButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            mapsButtons[i] = new Button("MAP " + (i + 1));
            mapsButtons[i].setStyle("-fx-background-color: transparent;");
            mapsButtons[i].setFont(Font.font("Comic Sans MS", 35));
            mapsButtons[i].setTextFill(Color.rgb(160, 160, 170));

            //buttons' events
            if (i == 3) {
                mapsButtons[i].setText("BACK");
                addButtonEffect(mapsButtons[3], Color.rgb(160, 160, 170), Color.rgb(229, 187, 229));
                mapsButtons[3].setOnAction(e -> {
                    stage.setScene(mainScene);
                    sound.start_sound.stop();
                    if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                        sound.btnSound.stop();
                        sound.btnSound.play();
                    } else {
                        sound.btnSound.play();
                    }
                });
            } else {
                int finalI = i;
                mapsButtons[i].setOnAction(e -> {
                    sound.start_sound.stop();
                    mapNumber = finalI + 1;
                    if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                        sound.btnSound.stop();
                        sound.btnSound.play();
                    } else {
                        sound.btnSound.play();
                    }
                    stage.setScene(mainScene);
                });
                mapsButtons[i].setOnMouseEntered(e -> {
                    // Styling
                    mapsButtons[finalI].setTextFill(Color.rgb(229, 187, 229));
                    mapsButtons[finalI].setScaleX(1.2);
                    mapsButtons[finalI].setScaleY(1.2);
                    // Sounds
                    if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                        sound.btnSound.stop();
                        sound.btnSound.play();
                    } else {
                        sound.btnSound.play();
                    }
                    maps[finalI].setVisible(true);
                });
                mapsButtons[i].setOnMouseExited(e -> {
                    // Effects
                    mapsButtons[finalI].setTextFill(Color.rgb(160, 160, 170));
                    mapsButtons[finalI].setScaleX(1);
                    mapsButtons[finalI].setScaleY(1);
                    // Sounds
                    if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                        sound.btnSound.stop();
                        sound.btnSound.play();
                    } else {
                        sound.btnSound.play();
                    }
                    maps[finalI].setVisible(false);
                });
            }

            mapsButtonsPane.getChildren().add(mapsButtons[i]);
        }

        //maps scene
        ImageView mapsBackground = new ImageView("mapsBackground.jpg");
        mapsBackground.setFitWidth(BOARD_WIDTH / 1.15);
        mapsBackground.setFitHeight(BOARD_HEIGHT / 1.25);
        Pane mapsPane = new Pane(mapsBackground, mapsButtonsPane, mapsImagesPane);
        mapsScene = new Scene(mapsPane, BOARD_WIDTH / 1.15, BOARD_HEIGHT / 1.25);

    }

    /*---------------------------------------------------*/

    // Establishing The Scene of Info (Switched by Info Bt)
    private void setInfoScene() {
        Pane infoPane = new Pane();

        Button backBtn = new Button("Back");
        backBtn.setBackground(Background.EMPTY);
        backBtn.setLayoutX(700);
        backBtn.setLayoutY(600);
        backBtn.setFont(Font.font(35));
        backBtn.setTextFill(Color.DARKMAGENTA);
        backBtn.setOnAction(e -> stage.setScene(mainScene));
        backBtn.setOnMouseEntered(e -> {
            if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                sound.btnSound.stop();
                sound.btnSound.play();
            } else {
                sound.btnSound.play();
            }
        });
        addButtonEffect(backBtn, Color.DARKMAGENTA, Color.rgb(139, 139, 139));
        // Background
        ImageView infoBackground = new ImageView("InfoBaackground.jpg");
        infoBackground.setFitWidth(900);
        infoBackground.setFitHeight(700);
        infoPane.getChildren().addAll(infoBackground, backBtn);

        // Members Nodes
        String[] membersStrs = {"Mahmoud El-Baz", "Abdelrahman El-Hussainy","Omar Tartour", "Osman El-Kinani",
                "Mahmoud Gamal","Mahmoud Hamdy", "Mazen Mohammed", "Ibrahim Ayman","Mohammed Wagih","Abdallah Mohamed"};
        Text[] membersTxt = new Text[10];
        ImageView[] membersQrs = {new ImageView("ElbazQr.jpg"), new ImageView("HussainyQr.jpg"),new ImageView("OmarQr.jpg"),
                new ImageView("OsmanQr.jpg"),new ImageView("HablassQr.jpg"),new ImageView("HamdyQr.jpg"),new ImageView("MazenQr.jpg")
                ,new ImageView("IbrahimQr.jpg"),new ImageView("WagihQr.jpg"),new ImageView("AbdallahQr.jpg")};
        for(int i = 0; i < 10; i++){
            membersTxt[i] = new Text(membersStrs[i]);
            membersTxt[i].setFont(Font.font("Garamond", FontWeight.NORMAL, FontPosture.REGULAR, 34));
            membersTxt[i].setFill(Color.web("#ffffff"));
            membersTxt[i].setStroke(Color.GRAY);
            membersTxt[i].setStrokeWidth(.2);
            membersTxt[i].setX(90);
            membersTxt[i].setY(52+68*i);
            membersQrs[i].setFitWidth(57);
            membersQrs[i].setFitHeight(57);
            membersQrs[i].setX(20);
            membersQrs[i].setY(20+68*i);
            infoPane.getChildren().addAll(membersTxt[i], membersQrs[i]);
        }
        // Qr - Text of GitHub
        ImageView githubQr = new ImageView("GithubQr.png");
        githubQr.setFitWidth(350);
        githubQr.setFitHeight(350);
        githubQr.setX(475);
        githubQr.setY(80);
        infoPane.getChildren().add(githubQr);

        Text githubTxt = new Text("Unlock The Project's Code \nBy Scanning This QR Code!\n" +
                                    "Dive Into The Heart of Our\nProject's Implementation");
        githubTxt.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, FontPosture.REGULAR, 30));
        githubTxt.setFill(Color.web("#ffd700"));
        githubTxt.setStroke(Color.BLACK);
        githubTxt.setStrokeWidth(.4);
        githubTxt.setX(465);
        githubTxt.setY(490);
        infoPane.getChildren().add(githubTxt);

        infoScene = new Scene(infoPane, 900, 700);
    }

    /*----------------Helper Methods (Generic)--------------------*/

    // Adding scaling effect to buttons`
    public void addButtonEffect(Button button, Color before, Color after) {
        button.setOnMouseEntered(event -> {
            button.setTextFill(after);
            button.setScaleX(1.2);
            button.setScaleY(1.2);
            if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                sound.btnSound.stop();
                sound.btnSound.play();
            } else {
                sound.btnSound.play();
            }
        });
        button.setOnMouseExited(event -> {
            button.setTextFill(before);
            button.setScaleX(1);
            button.setScaleY(1);
        });
    }

    public void setLevelScene(){

        //set background
        ImageView LevelImageView = new ImageView("levelPic.jpg" );
        LevelImageView.setFitWidth(BOARD_WIDTH/1.15);
        LevelImageView.setFitHeight(BOARD_HEIGHT/1.25);

        // array of Buttons
        Button[] levelBtns = new Button[5];

        Polygon buttonShape = new Polygon(0, 40, 50, 0, 170, 0, 220, 40, 170, 80, 50, 80);

        //---------------------------------- main Buttons design ----------------------------------//
        String[] levelBtnsTxt = {"BEGINNER","EASY","NORMAL" ,"HARD" , "INSANE"};

        for (int i = 0; i < 5; i++) {
            levelBtns[i] = new Button(levelBtnsTxt[i]);

            levelBtns[i].setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 20));
            levelBtns[i].setTextFill(Color.rgb(255,215,0));
            levelBtns[i].setStyle("-fx-background-color:transparent ; -fx-border-color:#f0f8ff ;-fx-border-width: 2.5;");
            levelBtns[i].setPrefSize(220, 80);
            levelBtns[i].setShape(buttonShape);

            // ---------------------------------- MouseEntered event ---------------------------------- //
            int finalI = i;
            levelBtns[i].setOnMouseEntered(e->{
                levelBtns[finalI].setTextFill(Color.BLACK);
                levelBtns[finalI].setStyle("-fx-background-color:orange ;");
                levelBtns[finalI].setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 28));
                if(sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING ){
                    sound.btnSound.stop();
                    sound.btnSound.play();
                }else{
                    sound.btnSound.play();
                }
            });
            // ---------------------------------- MouseExited event ---------------------------------- //
            levelBtns[finalI].setOnMouseExited(e->{
                levelBtns[finalI].setTextFill(Color.rgb(255,215,0));
                levelBtns[finalI].setStyle("-fx-background-color:transparent ; -fx-border-color:#f0f8ff ;-fx-border-width: 2.5;");
                levelBtns[finalI].setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 20));
            });

            /*-------------------------set button actions --------------------------*/
            levelBtns[i].setOnAction(e->{
                level = finalI+1 ;
                stage.setScene(mainScene);
            });

        }

        //----------------------------- Pane of Buttons-------------------------------/
        GridPane gridButtonsPane = new GridPane();
        gridButtonsPane.addColumn(0, levelBtns[0], levelBtns[1]);
        gridButtonsPane.addColumn(1, levelBtns[3], levelBtns[4]);
        gridButtonsPane.setHgap(130);
        gridButtonsPane.setVgap(5);

        StackPane buttonsPane = new StackPane(gridButtonsPane , levelBtns[2]);
        buttonsPane.setLayoutX(46);
        buttonsPane.setLayoutY(170);



        Pane levePane = new Pane();
        levePane.getChildren().addAll(LevelImageView,buttonsPane);

        levelScene = new Scene(levePane,BOARD_WIDTH/1.15,BOARD_HEIGHT/1.25);

    }
}
