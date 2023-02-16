package com.chuidiang.pruebas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class play {

    static boolean pausa = false;

    public static void main(String[] args) throws FileNotFoundException,
            JavaLayerException, InterruptedException {
        final Player pl = new Player(new FileInputStream(
                "src/alonso.mp3"));

        new Thread() {
            public void run() {
                try {
                    while (true) {
                        if (!pausa) {
                            if (!pl.play(1)) {
                                break;
                            }
                        }
                    }
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        Thread.sleep(5000);
        pausa = true;
        System.out.println("pausado");
        Thread.sleep(2000);
        pausa = false;
    }
}