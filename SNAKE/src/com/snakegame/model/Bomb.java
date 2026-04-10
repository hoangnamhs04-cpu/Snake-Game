package com.snakegame.model;

import java.awt.Color;
import java.awt.Graphics;
import com.snakegame.utils.Constants;

public class Bomb extends GameObject {

    public Bomb(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        // 1. Vẽ thân bom màu đen
        g.setColor(Color.BLACK);
        g.fillOval(x, y, Constants.UNIT_SIZE, Constants.UNIT_SIZE);

        // 2. Vẽ viền màu đỏ để cảnh báo người chơi
        g.setColor(Color.RED);
        g.drawOval(x, y, Constants.UNIT_SIZE, Constants.UNIT_SIZE);

        // 3. Vẽ một đốm trắng nhỏ tạo hiệu ứng phản quang (Điểm cộng thẩm mỹ)
        g.setColor(Color.WHITE);
        g.fillOval(x + 5, y + 5, 5, 5);
    }
}