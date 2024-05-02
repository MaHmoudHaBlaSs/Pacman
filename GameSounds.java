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
    public GameSounds() {
        // create background music
        File soundFile = new File("C:\\Users\\Abdal\\IdeaProjects\\helloapplication\\src\\main\\resources\\start.mp3");
        Media sound = new Media(soundFile.toURI().toString());
        start_sound = new MediaPlayer(sound);
        start_sound.setVolume(.3);


        // create eat PELLET & POWERPELLET sound
        File eatPelletFile = new File("C:\\Users\\Abdal\\IdeaProjects\\helloapplication\\src\\main\\resources\\munch_1.mp3");
        Media soundEatPELLET = new Media(eatPelletFile.toURI().toString());
        eatPellet = new MediaPlayer(soundEatPELLET);
        eatPellet.setVolume(.7);


        // create death sound
        File death = new File("C:\\Users\\Abdal\\IdeaProjects\\helloapplication\\src\\main\\resources\\pacman_death.mp3");
        Media death_Sound = new Media(death.toURI().toString());
        deathSound = new MediaPlayer(death_Sound);
        deathSound.setVolume(.5);

        //create buttons sound
        File btnFile = new File("C:\\Users\\Abdal\\IdeaProjects\\helloapplication\\src\\main\\resources\\btnSound.mp3");
        Media btn = new Media(btnFile.toURI().toString());
        btnSound = new MediaPlayer(btn);
        btnSound.setVolume(.5);



    }

}
