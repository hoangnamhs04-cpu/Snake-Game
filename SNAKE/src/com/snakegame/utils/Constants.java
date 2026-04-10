package com.snakegame.utils;

import java.awt.Color;

public class Constants {
    // 1. Cấu hình kích thước màn hình
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static final int HUD_HEIGHT = 100; 
    public static final int TOTAL_HEIGHT = SCREEN_HEIGHT + HUD_HEIGHT;
    
    // 2. Cấu hình lưới (Grid)
    public static final int UNIT_SIZE = 25; 
    
    // 3. Cấu hình thời gian
    public static final int DELAY = 250; 
    
    // 4. Cấu hình màu sắc (Nhóm lại để tránh Duplicate)
    public static final Color DARK_BG = new Color(25, 25, 25);   
    public static final Color LIGHT_BG = Color.WHITE; 
    
    public static final Color COLOR_SNAKE_HEAD = new Color(50, 205, 50);
    public static final Color COLOR_SNAKE_BODY = new Color(34, 139, 34);
    public static final Color COLOR_GOLD = new Color(255, 215, 0); // Màu vàng cho Score/Level
    
    public static final Color FOOD_COLOR = Color.RED;
    public static final Color BONUS_COLOR = Color.MAGENTA;
    public static final Color BOMB_COLOR = Color.BLACK;
    
    // 5. Cấu hình âm thanh
    public static final String BGM_PATH = "res/music/background.wav";
    public static final String EAT_SOUND = "res/music/eat.wav";
    public static final String OVER_SOUND = "res/music/gameover.wav";
}