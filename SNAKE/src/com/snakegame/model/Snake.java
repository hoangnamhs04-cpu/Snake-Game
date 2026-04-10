package com.snakegame.model;

import com.snakegame.utils.Constants;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject {
    private List<Point> body;
    private char direction = 'R'; // 'U', 'D', 'L', 'R'

    public Snake(int x, int y) {
        super(x, y);
        body = new ArrayList<>();
        // Khởi tạo rắn ban đầu có 3 đốt
        for (int i = 0; i < 3; i++) {
            body.add(new Point(x - i * Constants.UNIT_SIZE, y));
        }
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < body.size(); i++) {
            if (i == 0) g.setColor(Color.GREEN); // Đầu rắn
            else g.setColor(new Color(45, 180, 0)); // Thân rắn
            g.fillRect(body.get(i).getX(), body.get(i).getY(), Constants.UNIT_SIZE, Constants.UNIT_SIZE);
        }
    }

    // Getter/Setter cho hướng và thân
    public char getDirection() { return direction; }
    public void setDirection(char direction) { this.direction = direction; }
    public List<Point> getBody() { return body; }
}