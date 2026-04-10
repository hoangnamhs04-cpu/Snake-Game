package com.snakegame.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Chúng ta kế thừa KeyAdapter và sẽ dùng MouseAdapter làm một biến nội bộ hoặc implements MouseListener
public class InputHandler extends KeyAdapter {
    private GameController controller;

    public InputHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        controller.processKey(e.getKeyCode());
    }

    
    public MouseAdapter getMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Sửa từ processMouseClick() thành handleMouseClick(...)
                // Truyền tọa độ x và y của chuột vào
                controller.handleMouseClick(e.getX(), e.getY());
            }
        };
    }
}