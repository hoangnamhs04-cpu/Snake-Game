package com.snakegame;

import com.snakegame.view.*;
import com.snakegame.controller.*;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GamePanel gamePanel = new GamePanel();
                GameController controller = new GameController(gamePanel);
                GameFrame frame = new GameFrame(gamePanel);
                InputHandler inputHandler = new InputHandler(controller);

                frame.addKeyListener(inputHandler);
                gamePanel.addMouseListener(inputHandler.getMouseListener());

                frame.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}