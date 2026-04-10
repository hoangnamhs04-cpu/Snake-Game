package com.snakegame.model;
import java.awt.*;
import com.snakegame.utils.Constants;

public class Obstacle extends GameObject {
    public Obstacle(int x, int y) { super(x, y); }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, Constants.UNIT_SIZE, Constants.UNIT_SIZE);
    }
}