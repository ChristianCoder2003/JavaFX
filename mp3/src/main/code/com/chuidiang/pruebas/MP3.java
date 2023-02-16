package com.chuidiang.pruebas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3 {

    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {

        Player apl = new Player(new FileInputStream("audios/alonso.mp3"));

        apl.play();
    }
}

