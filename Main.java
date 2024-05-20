package org.example.gamedemo;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
import java.util.Objects;

public class Main extends Application {
    private static final int BOARD_WIDTH = 760;
    private static final int BOARD_HEIGHT = 760;
    private static Stage stage;
    private Scene mainScene;
    private Scene charactersScene;
    private Scene mapsScene;
    private Scene infoScene;
    private Scene levelScene;
    private GameSounds sound;
    private MediaPlayer introPlayer;
    private Button[] mainMenuBtns;
    private int level = 1;
    private boolean introFinished = false;
    private int mapNumber = 1;
    private int pacmanGifNum = 1;

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
        primaryStage.getIcons().add(new Image("GameIcon.png"));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /*------------------Establishing Scenes------------------*/

    // Establishing the Scene of Main Menu
    private void setMainScene() {
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
        setTxtPosition(introTxts[0], 205, 50);
        setTxtPosition(introTxts[1], 290, 110);
        setTxtPosition(introTxts[2], 195, 565);

        mainMenuPane.getChildren().addAll(introView, introTxts[0], introTxts[1], introTxts[2]);

        Timeline txtTl = new Timeline(new KeyFrame(Duration.seconds(7.5), event ->{
            for (int i = 0; i < 3; i++){
                txtFd[i].play();
                txtSt[i].play();
            }
        }));
        txtTl.setCycleCount(1);
        txtTl.play();

        Timeline introTl = new Timeline(new KeyFrame(Duration.seconds(27.8), event ->{
            for(int i = 0; i < 3; i++){
                txtFd[i].stop();
                txtSt[i].stop();
            }
            introPlayer.stop();
            mainMenuPane.getChildren().clear();
            introFinished = true;
        }));
        introTl.setOnFinished(event -> {

            // Setting The mainMenu Pane
            mainMenuPane.getChildren().clear();
            mainMenuPane.getChildren().addAll(mainMenuNodes());

        });
        introTl.setCycleCount(1);
        introTl.play();

        mainScene.setOnKeyPressed(keyEvent -> {
            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                if (!introFinished) {
                    for (int i = 0; i < 3; i++) {
                        txtFd[i].stop();
                        txtSt[i].stop();
                    }
                    introPlayer.stop();

                    // Setting The mainMenu Pane
                    mainMenuPane.getChildren().clear();
                    mainMenuPane.getChildren().addAll(mainMenuNodes());

                    introFinished = true;
                }
            }
        });
    }

    //---------Main Menu Establishing Methods---------//
    // Returning Main Menu Nodes [Buttons Pane - Palestine Pane - Background]
    private ObservableList<Node> mainMenuNodes(){
        Pane mainMenuPane = new Pane();
        // Set Background
        ImageView mainImageView = new ImageView("mainMenuPic.jpg");
        mainImageView.setFitWidth(BOARD_WIDTH / 1.14);
        mainImageView.setFitHeight(BOARD_HEIGHT / 1.25);

        // Set The Panes
        Pane btnsPane = mainMenuBtnsPane();
        // Set Palestine
        Pane palestinePane = palestinePane();
        mainMenuPane.getChildren().addAll(mainImageView, btnsPane, palestinePane);
        return mainMenuPane.getChildren();
    }
    // Returning Main menu Buttons Pane
    private Pane mainMenuBtnsPane() {
        Polygon buttonShape1 = new Polygon(0, 0, 200, 0, 230, 30, 30, 30);
        Polygon buttonShape2 = new Polygon(30, 0, 230, 0, 200, 30, 0, 30);

        //set the buttons' array, Vbox for them
        VBox buttonsPane = new VBox(8);
        buttonsPane.setLayoutX(39);
        buttonsPane.setLayoutY(352);

        mainMenuBtns = new Button[5];
        String[] btnsText = {"Play", "CHARACTERS", "MAPS", "LEVEL", "INFO"};

        for (int i = 0; i < 5; i++) {
            //styling
            mainMenuBtns[i] = new Button(btnsText[i]);
            mainMenuBtns[i].setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 20));
            mainMenuBtns[i].setStyle("-fx-background-color:black ; -fx-border-color:#a0a0aa ;-fx-border-width: 0.7;");
            mainMenuBtns[i].setTextFill(Color.DARKTURQUOISE);
            mainMenuBtns[i].setPrefSize(200, 30);
            mainMenuBtns[i].setShape(i % 2 == 0 ? buttonShape1 : buttonShape2);


            buttonsPane.getChildren().add(mainMenuBtns[i]);

            //styling events
            int finalI = i;
            mainMenuBtns[i].setOnMouseEntered(e -> {
                mainMenuBtns[finalI].setTextFill(Color.BLACK);
                mainMenuBtns[finalI].setStyle("-fx-background-color:orange ;");
                mainMenuBtns[finalI].setPrefSize(240, 40);
                if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                    sound.btnSound.stop();
                    sound.btnSound.play();
                } else {
                    sound.btnSound.play();
                }
            });
            mainMenuBtns[i].setOnMouseExited(e -> {
                mainMenuBtns[finalI].setTextFill(Color.DARKTURQUOISE);
                mainMenuBtns[finalI].setStyle("-fx-background-color:black; -fx-border-color:snow; -fx-border-width: 0.5;");
                mainMenuBtns[finalI].setPrefSize(200, 30);
            });


        }

        // Navigation
        mainMenuBtns[0].setOnAction(e -> {
            System.gc();
            stage.setScene(new GamePane(mapNumber,sound,level,pacmanGifNum,stage,mainScene).getScene());
            sound.start_sound.stop();
        });
        mainMenuBtns[1].setOnAction(e -> {
            stage.setScene(charactersScene);
            sound.start_sound.stop();
        });
        mainMenuBtns[2].setOnAction(e -> {
            stage.setScene(mapsScene);
            sound.start_sound.stop();
        });
        mainMenuBtns[3].setOnAction(e -> {
            stage.setScene(levelScene);
            sound.start_sound.stop();
        });
        mainMenuBtns[4].setOnAction(e -> {
            stage.setScene(infoScene);
            sound.start_sound.stop();
        });
        return buttonsPane;
    }
    // Returning Main menu Palestine Pane
    private Pane palestinePane(){
        HBox palestinePane = new HBox();

        // Set Palestine Flag
        ImageView[] caseView = {new ImageView("TheCase.png"), new ImageView("TheCase.png")};
        for(int i = 0; i < 2; i++){
            setImgDimensions(caseView[i], 50, 50);
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
    /*------------------------------------------------------*/

    // Establishing the Scene of Characters (Switched by Characters Bt)
    private void setCharactersScene() {

        //pane for characters' gifs
        StackPane charactersPane = charactersGifsPane();

        //pane for buttons to choose a character gif
        VBox charactersBtnsVbox = charactersBtnsPane(charactersPane);

        //set main pane , add buttons' pane and characters' pane to it
        Pane charactersMenuPane = new Pane();
        ImageView characterBackground = new ImageView("CharacterBackground.jpg");
        setImgDimensions(characterBackground, BOARD_WIDTH / 1.15, BOARD_HEIGHT / 1.25);
        charactersMenuPane.getChildren().addAll(characterBackground, charactersBtnsVbox, charactersPane);

        //create the scene and add the main pane to it
        charactersScene = new Scene(charactersMenuPane, BOARD_WIDTH / 1.15, BOARD_HEIGHT / 1.25);

    }

    //--------Character Establishing Methods-------//
    // Returning Vbox of characters' buttons at [Character Scene]
    private VBox charactersBtnsPane(StackPane charactersPane) {
        VBox charactersBtnsVbox = new VBox(25);
        charactersBtnsVbox.setAlignment(Pos.CENTER_LEFT);
        charactersBtnsVbox.setLayoutX(0);
        charactersBtnsVbox.setLayoutY(200);
        charactersBtnsVbox.setPadding(new Insets(10));


        // Setting Characters Buttons
        Button[] charactersBtns = new Button[4];
        String[] btnsText = {"Pacman", "Pacwoman", "Pacboy", "Back"};
        for (int i = 0; i < 3; i++) {
            //set button style
            charactersBtns[i] = new Button(btnsText[i]);
            charactersBtns[i].setStyle("-fx-background-color: transparent;");
            charactersBtns[i].setFont(new Font("Comic Sans MS", 30));
            charactersBtns[i].setTextFill(Color.CYAN);

            // Set The Button Events
            int finalI = i;
            charactersBtns[i].setOnAction(event -> {
                sound.start_sound.stop();
                pacmanGifNum = finalI + 1;
                stage.setScene(mainScene);
            });
            charactersBtns[i].setOnMouseEntered(event -> {
                charactersPane.getChildren().get(finalI).setVisible(true);
                if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                    sound.btnSound.stop();
                    sound.btnSound.play();
                } else {
                    sound.btnSound.play();
                }
            });
            charactersBtns[i].setOnMouseExited(event -> {
                charactersPane.getChildren().get(finalI).setVisible(false);
            });

            //add the button to the vbox
            charactersBtnsVbox.getChildren().add(charactersBtns[i]);
        }
        for(int i = 0; i < 3; i++){
            addButtonEffect(charactersBtns[i], Color.CYAN, Color.MAGENTA);
        }

        // Setting the Back Button
        charactersBtns[3] = new Button(btnsText[3]);
        charactersBtns[3].setFont(Font.font("Comic Sans MS", 30));
        charactersBtns[3].setBackground(Background.EMPTY);
        charactersBtns[3].setTextFill(Color.DARKRED);

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
        addButtonEffect(charactersBtns[3], Color.DARKRED, Color.RED);

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

        //set the gif for every character
        String[] gifsUrls = {"PacmanEye.gif", "pacwoman.gif", "pacboy.gif"};
        ImageView[] charactersGifs = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            charactersGifs[i] = new ImageView(gifsUrls[i]);
            setImgDimensions(charactersGifs[i], 150, 150);
            charactersGifs[i].setTranslateY(160);
            charactersGifs[i].setTranslateX(130);
            charactersGifs[i].setVisible(false);
            charactersPane.getChildren().add(charactersGifs[i]);
        }

        return charactersPane;
    }
    /*------------------------------------------------------*/

    // Establishing the Scene of Maps (Switched by Maps Bt)
    private void setMapScene() {

        //maps images, pane for them
        ImageView[] maps = {new ImageView("map1.png"), new ImageView("map2.png"), new ImageView("map3.png")};
        StackPane mapsImagesPane = new StackPane(maps[0], maps[1], maps[2]);
        mapsImagesPane.setLayoutX(300);
        mapsImagesPane.setLayoutY(150);
        mapsImagesPane.setBackground(Background.EMPTY);

        //maps styling
        for (int i = 0; i < 3; i++) {
            setImgDimensions(maps[i], 280, 280);
            maps[i].setVisible(false);
        }


        //Set Buttons And Vbox For Them
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
                    addButtonScaleEffect(mapsButtons[finalI], 1.2, 1.2, Color.rgb(229, 187, 229));
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
                    addButtonScaleEffect(mapsButtons[finalI], 1, 1, Color.rgb(160, 160, 170));
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
        setImgDimensions(mapsBackground, BOARD_WIDTH / 1.15, BOARD_HEIGHT / 1.25);
        Pane mapsPane = new Pane(mapsBackground, mapsButtonsPane, mapsImagesPane);
        mapsScene = new Scene(mapsPane, BOARD_WIDTH / 1.15, BOARD_HEIGHT / 1.25);

    }
    /*------------------------------------------------------*/
    // Establishing The Scene of Levels (Switched by Level Bt)
    private void setLevelScene(){

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
    /*------------------------------------------------------*/

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
        ImageView infoBackground = new ImageView("InfoBackground.jpg");
        setImgDimensions(infoBackground, 900, 700);
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
            membersTxt[i].setStyle("-fx-font-family: 'Trebuchet MS'; -fx-font-size: 30");
            membersTxt[i].setFill(Color.web("#f5f5dc"));
            membersTxt[i].setStroke(Color.GRAY);
            membersTxt[i].setStrokeWidth(.2);
            setTxtPosition(membersTxt[i], 90, 52+68*i);
            adjustImg(membersQrs[i], 57, 57, 20, 20+68*i);
            infoPane.getChildren().addAll(membersTxt[i], membersQrs[i]);
        }
        // Qr - Text of GitHub
        ImageView githubQr = new ImageView("GithubQr.png");
        adjustImg(githubQr, 350, 350, 525, 100);
        infoPane.getChildren().add(githubQr);

        Text githubTxt = new Text("Unlock The Project's Code \nBy Scanning This QR Code!\n" +
                                    "Dive Into The Heart of Our\nProject's Implementation");
        githubTxt.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, FontPosture.REGULAR, 25));
        githubTxt.setFill(Color.web("#ffd700"));
        githubTxt.setStroke(Color.BLACK);
        githubTxt.setStrokeWidth(.4);
        setTxtPosition(githubTxt, 547, 490);
        infoPane.getChildren().add(githubTxt);

        infoScene = new Scene(infoPane, 900, 700);
    }

    /*----------------Helper Methods (Generic)--------------------*/
    private void setTxtPosition(Text txt, double x, double y){
        txt.setX(x);
        txt.setY(y);
    }
    private void setImgDimensions(ImageView img, double width, double height){
        img.setFitWidth(width);
        img.setFitHeight(height);
    }
    private void adjustImg(ImageView img, double width, double height,double x, double y){
        img.setFitWidth(width);
        img.setFitHeight(height);
        img.setX(x);
        img.setY(y);
    }
    private void addButtonScaleEffect(Button btn, double scaleX, double scaleY, Color newColor){
        btn.setScaleX(scaleX);
        btn.setScaleY(scaleY);
        btn.setTextFill(newColor);
    }
    // Adding Scaling Effect To Button
    private void addButtonEffect(Button button, Color before, Color after) {
        button.setOnMouseEntered(event -> {
            addButtonScaleEffect(button, 1.2, 1.2, after);
            if (sound.btnSound.getStatus() == MediaPlayer.Status.PLAYING) {
                sound.btnSound.stop();
                sound.btnSound.play();
            } else {
                sound.btnSound.play();
            }
        });
        button.setOnMouseExited(event -> {
            addButtonScaleEffect(button, 1, 1, before);
        });
    }
}
