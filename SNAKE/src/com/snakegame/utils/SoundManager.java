package com.snakegame.utils;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    private Clip backgroundMusic;

    // Hàm phát nhạc nền (lặp lại vô tận)
    public void playBackgroundMusic(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                File soundFile = new File ("assets/background.wav");
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioInput);
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); // Lặp lại vô tận
                backgroundMusic.start();
            } else {
                System.out.println("Không tìm thấy file nhạc tại: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm phát âm thanh ngắn (ăn mồi, trúng bom)
    public void playSoundEffect(String filePath) {
        try {
            File path = new File(filePath);
            if (path.exists()) {
                File soundFile = new File ("assets/eat.wav");
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }
}