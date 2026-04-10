package com.snakegame.view;

import com.snakegame.model.*;
import com.snakegame.model.Point;
import com.snakegame.utils.Constants;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    // Các biến trạng thái cài đặt
    public boolean isDarkMode = true;
    public boolean soundOn = true;
    public boolean vibrateOn = true;
    public boolean showSettings = false;

    private Snake snake;
    private Food food;
    private List<Obstacle> obstacles;
    private int score, highScore, level;
    private boolean running;
    private BonusFood bonusFood;
    private int countdownValue;
    private boolean inMenu = true;
    private boolean inModernMode;
    private Bomb bomb;
    private int mapPadding = 0; 

    public GamePanel() {
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.TOTAL_HEIGHT));
        this.setFocusable(true);
    }

    
    public void updateData(Snake s, Food f, BonusFood bf, List<Obstacle> obs, 
                       int sc, int hs, int lv, boolean r, 
                       boolean im, Bomb b, boolean isModern, int cv, int mp) {
        this.snake = s;
        this.food = f;
        this.bonusFood = bf;
        this.obstacles = obs;
        this.score = sc;
        this.highScore = hs;
        this.level = lv;
        this.running = r;
        this.inMenu = im;
        this.bomb = b;
        this.inModernMode = isModern;
        this.countdownValue = cv;
        this.mapPadding = mp;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (inMenu) { 
            drawMenu(g2d);
            return; 
        }

        g2d.translate(0, Constants.HUD_HEIGHT);
        drawPlayArea(g2d);
        g2d.translate(0, -Constants.HUD_HEIGHT);

        drawHUD(g2d);

        if (showSettings) drawSettingsMenu(g2d);
        if (!running) drawGameOver(g2d);

        if (countdownValue > 0) {
            drawCountdown(g2d); 
        }
    }

    private void drawGrid(Graphics g) {
        // Thiết lập màu sắc cho lưới (màu xám mờ để không gây chói mắt)
        g.setColor(new Color(255, 255, 255, 30)); 

        // Vẽ các đường dọc
        // Chúng ta vẽ từ mapPadding để thấy rõ vùng an toàn
        for (int i = mapPadding; i <= Constants.SCREEN_WIDTH - mapPadding; i += Constants.UNIT_SIZE) {
            g.drawLine(i, mapPadding, i, Constants.SCREEN_HEIGHT - mapPadding);
        }

        // Vẽ các đường ngang
        for (int i = mapPadding; i <= Constants.SCREEN_HEIGHT - mapPadding; i += Constants.UNIT_SIZE) {
            g.drawLine(mapPadding, i, Constants.SCREEN_WIDTH - mapPadding, i);
        }
        
        // Vẽ một khung đậm bao quanh vùng mapPadding để xác định ranh giới chết
        g.setColor(Color.RED);
        g.drawRect(mapPadding, mapPadding, 
                Constants.SCREEN_WIDTH - 2 * mapPadding, 
                Constants.SCREEN_HEIGHT - 2 * mapPadding);
    }
    
    private void drawPlayArea(Graphics2D g) {
        // 1. Vẽ nền 
        g.setColor(isDarkMode ? Constants.DARK_BG : Constants.LIGHT_BG);
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // 2. Vẽ lưới
        g.setColor(isDarkMode ? new Color(40, 40, 40) : new Color(210, 210, 210));
        for (int i = 0; i <= Constants.SCREEN_WIDTH / Constants.UNIT_SIZE; i++) {
            g.drawLine(i * Constants.UNIT_SIZE, 0, i * Constants.UNIT_SIZE, Constants.SCREEN_HEIGHT);
            g.drawLine(0, i * Constants.UNIT_SIZE, Constants.SCREEN_WIDTH, i * Constants.UNIT_SIZE);
        }

        if (snake == null || food == null) return;

        if (running) {
            drawSnakeWithEyes(g);
            food.draw(g);
            
            if (obstacles != null) {
                g.setColor(Color.GRAY);
                for (Obstacle obs : obstacles) obs.draw(g);
            }
        }

        if (bonusFood != null) {
            bonusFood.draw(g); 
            
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("!!! BONUS !!!", bonusFood.getX() - 10, bonusFood.getY() - 5);
        }

        if (inModernMode) { 
            g.setColor(Color.GRAY);
            for (Obstacle ob : obstacles) {
                g.fillRect(ob.getX() * Constants.UNIT_SIZE, ob.getY() * Constants.UNIT_SIZE, 
                        Constants.UNIT_SIZE, Constants.UNIT_SIZE);
            }

            if (bomb != null) {
                g.setColor(Color.BLUE);
                g.fillOval(bomb.getX(), bomb.getY(), Constants.UNIT_SIZE, Constants.UNIT_SIZE);
                // Viền đỏ
                g.setColor(Color.CYAN);
                g.fillRect(bomb.getX() + (Constants.UNIT_SIZE/2) - 2, bomb.getY() - 4, 4, 6);
            }
        }

        // 3. VẼ DẤU "X" CẢNH BÁO CHO MODERN MODE
        if (inModernMode && mapPadding > 0) {
            g.setColor(new Color(255, 0, 0, 180)); // Màu đỏ rõ nét
            g.setFont(new Font("Arial", Font.BOLD, Constants.UNIT_SIZE - 4));
            
            int unit = Constants.UNIT_SIZE;
            int cellsX = Constants.SCREEN_WIDTH / unit;
            int cellsY = Constants.SCREEN_HEIGHT / unit;
            int padCells = mapPadding / unit; 

            for (int i = 0; i < cellsX; i++) {
                for (int j = 0; j < cellsY; j++) {
                    if (i < padCells || i >= cellsX - padCells ||
                        j < padCells || j >= cellsY - padCells) {
                            int xPos = i * unit;
                            int yPos = j * unit;
                            g.drawString("X", xPos + 4, yPos + unit - 4);
                        }
                }
            }
            g.setStroke(new BasicStroke(2));
            g.drawRect(mapPadding, mapPadding, 
                    Constants.SCREEN_WIDTH - 2 * mapPadding, 
                    Constants.SCREEN_HEIGHT - 2 * mapPadding);
        }
    }

    
    private void drawCountdown(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.TOTAL_HEIGHT);

        g.setFont(new Font("Arial", Font.BOLD, 120));
        String text = String.valueOf(countdownValue);

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (Constants.SCREEN_WIDTH - metrics.stringWidth(text)) / 2;
        int y = (Constants.TOTAL_HEIGHT - metrics.getHeight()) / 2 + metrics.getAscent();

        g.setColor(new Color(0, 0, 0, 200));
        g.drawString(text, x + 5, y + 5);

        g.setColor(new Color(255, 215, 0)); 
        g.drawString(text, x, y);
        
        g.setFont(new Font("Arial", Font.ITALIC, 20));
        g.setColor(Color.WHITE);
        String subText = "Chuẩn bị...";
        int subX = (Constants.SCREEN_WIDTH - g.getFontMetrics().stringWidth(subText)) / 2;
        g.drawString(subText, subX, y + 60);
    }

    private void drawMenu(Graphics2D g) {
        // 1. Vẽ nền tối cho Menu
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.TOTAL_HEIGHT);

        // 2. Vẽ tiêu đề SNAKE chính giữa
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.setColor(Color.GREEN);
        String title = "SNAKE";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (Constants.SCREEN_WIDTH - titleWidth) / 2, 150);

        // 3. Vẽ nút PLAY chính giữa
        drawStyledButton(g, "PLAY", 300);

        // 4. THÊM DÒNG CHỮ CREDIT Ở DƯỚI CÙNG (Căn giữa)
        g.setFont(new Font("Arial", Font.ITALIC, 18)); // Font chữ nhỏ hơn và in nghiêng
        g.setColor(Color.WHITE); // Màu trắng cho dễ đọc trên nền tối
        
        String credits = "By: Văn Thức, Minh Thành, Hoàng Nam";
        int creditsWidth = g.getFontMetrics().stringWidth(credits);
        
        // Tọa độ X căn giữa, Tọa độ Y nằm cách mép dưới khoảng 30-50 pixel
        int creditsX = (Constants.SCREEN_WIDTH - creditsWidth) / 2;
        int creditsY = Constants.TOTAL_HEIGHT - 40; 
        
        g.drawString(credits, creditsX, creditsY);
    }

    private void drawStyledButton(Graphics2D g, String text, int y) {
        int width = 250;
        int height = 50;
        int x = (Constants.SCREEN_WIDTH - width) / 2;

        // 1. Vẽ bóng đổ (Bạn đã có)
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(x + 3, y + 3, width, height, 15, 15);

        // 2. Vẽ thân nút màu xanh
        g.setColor(new Color(34, 139, 34));
        g.fillRoundRect(x, y, width, height, 15, 15);

        // 3. Vẽ viền trắng
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        g.drawRoundRect(x, y, width, height, 15, 15);

        // 4. Vẽ chữ (Quan trọng)
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = g.getFontMetrics();
        int textX = x + (width - metrics.stringWidth(text)) / 2;
        int textY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(text, textX, textY);
    }

    private void drawSnakeWithEyes(Graphics2D g) {
        List<Point> body = snake.getBody();
        for (int i = 0; i < body.size(); i++) {
            Point p = body.get(i);
            if (i == 0) { // Đầu rắn
                g.setColor(Constants.COLOR_SNAKE_HEAD);
                g.fillRoundRect(p.getX(), p.getY(), Constants.UNIT_SIZE, Constants.UNIT_SIZE, 12, 12);
                
                // Vẽ mắt và miệng (Yêu cầu của bạn)
                g.setColor(Color.BLACK);
                int s = Constants.UNIT_SIZE;
                if (snake.getDirection() == 'R') {
                    g.fillOval(p.getX() + s - 8, p.getY() + 5, 4, 4); // Mắt trên
                    g.fillOval(p.getX() + s - 8, p.getY() + s - 9, 4, 4); // Mắt dưới
                } else if (snake.getDirection() == 'L') {
                    g.fillOval(p.getX() + 4, p.getY() + 5, 4, 4);
                    g.fillOval(p.getX() + 4, p.getY() + s - 9, 4, 4);
                } else if (snake.getDirection() == 'U') {
                    g.fillOval(p.getX() + 5, p.getY() + 4, 4, 4);
                    g.fillOval(p.getX() + s - 9, p.getY() + 4, 4, 4);
                } else {
                    g.fillOval(p.getX() + 5, p.getY() + s - 8, 4, 4);
                    g.fillOval(p.getX() + s - 9, p.getY() + s - 8, 4, 4);
                }
            } else { // Thân rắn bo góc
                g.setColor(Constants.COLOR_SNAKE_BODY);
                g.fillRoundRect(p.getX(), p.getY(), Constants.UNIT_SIZE, Constants.UNIT_SIZE, 8, 8);
            }
        }
    }
    
    private void drawHUD(Graphics2D g) {
        // 1. Vẽ nền cho thanh HUD (Tùy chọn DarkMode)
        g.setColor(isDarkMode ? new Color(30, 30, 30) : new Color(200, 200, 200));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.HUD_HEIGHT);

        // 2. Thiết lập Font và Màu cho chữ
        g.setColor(isDarkMode ? Color.WHITE : Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        
        // VẼ DUY NHẤT 1 LẦN (Đã căn chỉnh tọa độ đẹp)
        g.drawString("SCORE: " + score, 20, 35);
        g.drawString("LEVEL: " + level, 20, 65);

        // 3. Vẽ Vương miện (Highscore) - Căn lề phải
        drawCrown(g, Constants.SCREEN_WIDTH - 160, 25);
        g.drawString(": " + highScore, Constants.SCREEN_WIDTH - 115, 50);

        // 4. Nút Setting hình bánh răng
        drawSettingsIcon(g, Constants.SCREEN_WIDTH - 50, 30);

        // 5. VẼ NÚT HOME (HÌNH NGÔI NHÀ)
        int hX = 160; 
        int hY = 25;
        int hSize = 40;

        // Nền nút Home (Bo tròn mờ)
        g.setColor(new Color(255, 255, 255, 50));
        g.fillRoundRect(hX, hY, hSize, hSize, 10, 10);

        // Icon ngôi nhà màu trắng
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        
        // Mái nhà
        g.drawPolygon(new int[]{hX + 5, hX + 20, hX + 35}, new int[]{hY + 15, hY + 5, hY + 15}, 3);
        // Thân nhà
        g.drawRect(hX + 10, hY + 15, 20, 18);
        // Cửa nhà
        g.fillRect(hX + 17, hY + 23, 6, 10);
    }
    

    private void drawCrown(Graphics2D g, int x, int y) {
        g.setColor(Constants.COLOR_GOLD);
        int[] xPts = {x, x+7, x+13, x+20, x+27, x+33, x+40, x+40, x};
        int[] yPts = {y+25, y+5, y+15, y, y+15, y+5, y+25, y+35, y+35};
        g.fillPolygon(xPts, yPts, 9);
    }

    private void drawSettingsIcon(Graphics2D g, int x, int y) {
        g.setColor(isDarkMode ? Color.WHITE : Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawOval(x, y, 20, 20);
        for(int i=0; i<8; i++) { // Vẽ các khía bánh răng
            double angle = Math.toRadians(i * 45);
            int x1 = x + 10 + (int)(8 * Math.cos(angle));
            int y1 = y + 10 + (int)(8 * Math.sin(angle));
            int x2 = x + 10 + (int)(12 * Math.cos(angle));
            int y2 = y + 10 + (int)(12 * Math.sin(angle));
            g.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawGameOver(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.TOTAL_HEIGHT);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("GAME OVER", 150, Constants.TOTAL_HEIGHT / 2);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("Click to Reset", 230, Constants.TOTAL_HEIGHT / 2 + 50);
    }
    
    private void drawSettingsMenu(Graphics2D g) {
        // Overlay mờ
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.TOTAL_HEIGHT);

        // Khung menu
        g.setColor(Color.WHITE);
        g.fillRoundRect(150, 150, 300, 350, 20, 20);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("SETTINGS", 235, 190);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        
        // Mục Sound
        g.drawString("SOUND:", 180, 250);
        drawOnOffToggle(g, 300, 235, soundOn);

        // Mục Vibrate
        g.drawString("VIBRATE:", 180, 310);
        drawOnOffToggle(g, 300, 295, vibrateOn);

        // Mục Color Mode
        g.drawString("MODE:", 180, 370);
        drawModeToggle(g, 260, 355, isDarkMode);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("CLOSE [X]", 255, 460);
    }

    private void drawOnOffToggle(Graphics2D g, int x, int y, boolean isOn) {
        // Nút ON
        g.setColor(isOn ? Color.GREEN : Color.LIGHT_GRAY);
        g.fillRect(x, y, 40, 25);
        g.setColor(Color.BLACK);
        g.drawString("ON", x + 7, y + 18);

        // Nút OFF
        g.setColor(!isOn ? Color.RED : Color.LIGHT_GRAY);
        g.fillRect(x + 50, y, 40, 25);
        g.setColor(Color.BLACK);
        g.drawString("OFF", x + 55, y + 18);
    }

    private void drawModeToggle(Graphics2D g, int x, int y, boolean isDark) {
        g.setColor(isDark ? Color.BLACK : Color.LIGHT_GRAY);
        g.fillRect(x, y, 70, 25);
        g.setColor(isDark ? Color.WHITE : Color.BLACK);
        g.drawString("DARK", x + 12, y + 18);

        g.setColor(!isDark ? Color.WHITE : Color.LIGHT_GRAY);
        g.drawRect(x + 80, y, 70, 25);
        g.fillRect(x + 80, y, 70, 25);
        g.setColor(Color.BLACK);
        g.drawString("LIGHT", x + 90, y + 18);
    }
}