package com.snakegame.model;
import java.awt.*;
import com.snakegame.utils.Constants;

public class Food extends GameObject {
    public Food(int x, int y) { super(x, y); }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, Constants.UNIT_SIZE, Constants.UNIT_SIZE);
    }
}