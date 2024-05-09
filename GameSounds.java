package com.example.helloapplication;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class GameSounds extends Pane {


    MediaPlayer start_sound;
    MediaPlayer eatPellet ;
    MediaPlayer deathSound;
    MediaPlayer btnSound;
    MediaPlayer winSound;
    public GameSounds() {
        // create background music
        File soundFile = new File("C:\\Users\\Abdal\\IdeaProjects\\pacman\\src\\main\\resources\\start.mp3");
        Media sound = new Media(soundFile.toURI().toString());
        start_sound = new MediaPlayer(sound);
        start_sound.setVolume(.3);


        // create eat PELLET & POWERPELLET sound
        File eatPelletFile = new File("C:\\Users\\Abdal\\IdeaProjects\\pacman\\src\\main\\resources\\munch_1.mp3");
        Media soundEatPELLET = new Media(eatPelletFile.toURI().toString());
        eatPellet = new MediaPlayer(soundEatPELLET);
        eatPellet.setVolume(.7);
        eatPellet.setCycleCount(1);


        // create death sound
        File death = new File("C:\\Users\\Abdal\\IdeaProjects\\pacman\\src\\main\\resources\\over.mp3");
        Media death_Sound = new Media(death.toURI().toString());
        deathSound = new MediaPlayer(death_Sound);
        deathSound.setVolume(1);
        deathSound.setCycleCount(1);

        //create buttons sound
        File btnFile = new File("C:\\Users\\Abdal\\IdeaProjects\\pacman\\src\\main\\resources\\btnSound.mp3");
        Media btn = new Media(btnFile.toURI().toString());
        btnSound = new MediaPlayer(btn);
        btnSound.setVolume(.5);
        btnSound.setCycleCount(1);


        //create win sound
        File WinFile = new File("C:\\Users\\Abdal\\IdeaProjects\\pacman\\src\\main\\resources\\bravo.mp3");
        Media win = new Media(WinFile.toURI().toString());
        winSound = new MediaPlayer(win);
        winSound.setVolume(1);
        winSound.setCycleCount(1);




    }

}
