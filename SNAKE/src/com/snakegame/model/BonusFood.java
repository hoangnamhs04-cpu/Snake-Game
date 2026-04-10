package com.snakegame.model;

import java.awt.Color;
import java.awt.Graphics;
import com.snakegame.utils.Constants;

public class BonusFood extends Food {
    private long spawnTime;          // Thời điểm xuất hiện
    private final long DURATION = 5000; // Tồn tại trong 5 giây

    public BonusFood(int x, int y) {
        super(x, y);
        this.spawnTime = System.currentTimeMillis();
    }

    // Kiểm tra xem đã quá 5 giây chưa
    public boolean isExpired() {
        return (System.currentTimeMillis() - spawnTime) > DURATION;
    }

    @Override
    public void draw(Graphics g) {
        // Vẽ hiệu ứng hào quang lấp lánh màu tím
        g.setColor(new Color(255, 0, 255, 80)); 
        g.fillOval(getX() - 3, getY() - 3, Constants.UNIT_SIZE + 6, Constants.UNIT_SIZE + 6);

        // Vẽ thân quả cầu to màu Hồng Tím (Magenta)
        g.setColor(Color.MAGENTA);
        g.fillOval(getX(), getY(), Constants.UNIT_SIZE, Constants.UNIT_SIZE);

        // Vẽ điểm sáng 3D
        g.setColor(Color.WHITE);
        g.fillOval(getX() + 4, getY() + 4, 6, 6);
    }
}