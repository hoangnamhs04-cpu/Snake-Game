package com.snakegame.model;

import java.awt.Graphics;

public abstract class GameObject {
    protected int x;
    protected int y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Phương thức trừu tượng để mỗi đối tượng tự vẽ chính nó
    public abstract void draw(Graphics g);

    // Getters and Setters (Tính đóng gói)
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
}