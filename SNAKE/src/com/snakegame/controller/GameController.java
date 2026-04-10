package com.snakegame.controller;

import com.snakegame.model.Snake;
import com.snakegame.model.BonusFood;
import com.snakegame.model.Food;
import com.snakegame.model.Obstacle;
import com.snakegame.model.Point;
import com.snakegame.view.GamePanel;
import com.snakegame.utils.*;
import com.snakegame.model.Bomb;
import javax.swing.*;
import java.awt.Window;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameController implements ActionListener {
    private Snake snake;
    private Food food;
    private List<Obstacle> obstacles;
    private GamePanel view;
    private Timer timer;
    private boolean running = false;
    private boolean isPaused = false;
    private int score = 0;
    private int highScoreClassic = 0;
    private int level = 1;
    private Random random;
    private BonusFood bonusFood = null;
    private int countdownValue = 0; 
    private Timer countdownTimer;   
    private Bomb bomb;
    private int mapPadding = 0;
    private FileManager fileManager = new FileManager();
    private boolean isEating = false;
    private long bombSpawnTime; 
    private long lastBombTime = 0;

    public enum GameMode { CLASSIC, MODERN }
    public GameMode currentMode = GameMode.CLASSIC;
    public boolean inMenu = true; // Trạng thái ở màn hình chờ

    private SoundManager soundManager = new SoundManager();

    public GameController(GamePanel view) {
        this.view = view;
        this.random = new Random();
        this.obstacles = new ArrayList<>();
        initGame();
    }

    public void initGame() {
        score = 0;
        level = 1;
        isPaused = false;
        mapPadding = 0;        
        obstacles.clear(); 
        bomb = null;
        soundManager.stopMusic();
        soundManager.playBackgroundMusic("assets/background.wav");

        if (this.currentMode == GameMode.MODERN) {
            generateObstacles(); 
            generateBomb();      
        }

        snake = new Snake(100, 100);
        spawnFood();
        
        highScoreClassic = fileManager.loadHighScore("highscore_classic.txt");

        if (timer != null) timer.stop();
        timer = new javax.swing.Timer(Constants.DELAY, this);
        timer.start();
        running = true;
    }

    public void spawnFood() {

        Point safePoint = getRandomPointInSafeArea();
        food = new Food((int)safePoint.getX(), (int)safePoint.getY());

    }

    private void spawnBomb() {
        Point p = getRandomPointInSafeArea();
        bomb = new Bomb((int)p.getX(), (int)p.getY()); 
        bombSpawnTime = System.currentTimeMillis(); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !isPaused && !view.showSettings) {
            long currentTime = System.currentTimeMillis();

            // CHỈ GỌI 1 LẦN DUY NHẤT [cite: 21, 28, 29]
            move();
            checkCollision(); 
            checkFood();
            updateBonusFood();

            // LOGIC BOM XANH BẤT NGỜ (Dùng lastBombTime để xóa cảnh báo vàng)
            if (bomb == null) {
                if (currentTime - lastBombTime > 5000) { 
                    if (random.nextInt(200) < 1) { 
                        spawnBomb();
                        lastBombTime = currentTime;
                    }
                }
            } else {
                if (currentTime - bombSpawnTime > 5000) {
                    bomb = null;
                    lastBombTime = currentTime;
                }
            }
        }

        // Cập nhật dữ liệu (Ép về Classic để mapPadding luôn là 0)
        view.updateData(
            snake, food, bonusFood, obstacles, score, highScoreClassic, level,
            running, inMenu, bomb, false, 
            countdownValue, 0 
        );
        view.repaint();
    }



    private void move() {
        List<Point> body = snake.getBody();
        Point head = body.get(0);
        Point newHead = new Point(head.getX(), head.getY());

        // Tính toán đầu mới dựa trên hướng
        switch (snake.getDirection()) {
            case 'U' -> newHead.setY(newHead.getY() - Constants.UNIT_SIZE);
            case 'D' -> newHead.setY(newHead.getY() + Constants.UNIT_SIZE);
            case 'L' -> newHead.setX(newHead.getX() - Constants.UNIT_SIZE);
            case 'R' -> newHead.setX(newHead.getX() + Constants.UNIT_SIZE);
        }

        body.add(0, newHead); 

        if (!isEating) {
            body.remove(body.size() - 1);
        } else {
            isEating = false; 
        }
    }

    private void checkCollision() {
        Point head = snake.getBody().get(0);

        // 1. Kiểm tra va chạm biên (Giữ nguyên logic đã fix cho bạn)
        if (head.getX() < 0 || head.getX() >= Constants.SCREEN_WIDTH || 
            head.getY() < 0 || head.getY() >= Constants.SCREEN_HEIGHT) {
            gameOver();
            return;
        }

        // 2. KIỂM TRA VA CHẠM VẬT CẢN (Fix lỗi đi xuyên qua)
        // Phải kiểm tra danh sách này cho cả chế độ Classic
        if (obstacles != null) {
            for (Obstacle obs : obstacles) {
                if (head.getX() == obs.getX() && head.getY() == obs.getY()) {
                    System.out.println("Đâm vào vật cản rồi!");
                    gameOver();
                    return;
                }
            }
        }

        // 3. Kiểm tra va chạm thân rắn
        for (int i = 1; i < snake.getBody().size(); i++) {
            if (head.getX() == snake.getBody().get(i).getX() && 
                head.getY() == snake.getBody().get(i).getY()) {
                gameOver();
                return;
            }
        }

        // 4. Logic va chạm BOM XANH (BLUE)
        if (bomb != null && head.getX() == bomb.getX() && head.getY() == bomb.getY()) {
            soundManager.playSoundEffect("assets/explosion.wav");
            score = Math.max(0, score - 2);
            List<Point> body = snake.getBody();
            for (int i = 0; i < 2; i++) {
                if (body.size() > 1) body.remove(body.size() - 1);
            }
            bomb = null;
            isEating = true;
            if (view.vibrateOn) vibrateFrame();
            handleHighScore();
        }
    }

    private void checkFood() {
        Point head = snake.getBody().get(0);

            if (head.getX() == food.getX() && head.getY() == food.getY()) {
                soundManager.playSoundEffect("assets/eat.wav");
                score++;
                isEating = true;
                spawnFood();
                handleHighScore(); 

                if (score % 5 == 0) {
                    level++;
                    addObstacles();
                    
                    
                    timer.setDelay(Math.max(40, timer.getDelay() - 10));
                }
        }

        // --- 2. XỬ LÝ ĂN MỒI BONUS ---
        if (bonusFood != null) {
            if (head.getX() == bonusFood.getX() && head.getY() == bonusFood.getY()) {
                soundManager.playSoundEffect("assets/eat.wav");
                score += 5;
                isEating = true;
                bonusFood = null;
                handleHighScore(); 
                level++;
                
                addObstacleAtRandom();
                
                timer.setDelay(Math.max(40, timer.getDelay() - 10));
                
                if (view.vibrateOn) {
                    vibrateFrame(); 
                }
            }
        }
    }

    private void addObstacleAtRandom() {
        int ox = random.nextInt(Constants.SCREEN_WIDTH / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
        int oy = random.nextInt(Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
        obstacles.add(new Obstacle(ox, oy));
    }

    private void handleHighScore() {
        if (score > highScoreClassic) {
            highScoreClassic = score;
            fileManager.saveHighScore(highScoreClassic, "highscore_classic.txt");
        }
    }


    private void updateBonusFood() {
        if (bonusFood == null) {
            if (score > 2 && random.nextInt(300) < 1) { 
                spawnBonusFood();
            }
        } else {
            if (bonusFood.isExpired()) {
                bonusFood = null;
            }
        }
    }

    private void spawnBonusFood() {
        int fx, fy;
        boolean overlap;
        do {
            overlap = false;
            fx = random.nextInt(Constants.SCREEN_WIDTH / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
            fy = random.nextInt(Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
            
            // Kiểm tra xem có trùng với vật cản không
            for (Obstacle obs : obstacles) {
                if (obs.getX() == fx && obs.getY() == fy) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);
        Point safePoint = getRandomPointInSafeArea();
        bonusFood = new BonusFood((int)safePoint.getX(), (int)safePoint.getY());
    }

    private Point getRandomPointInSafeArea() {
        int fx, fy;
        boolean invalid;
        
        int minPos = mapPadding; 
        int maxX = Constants.SCREEN_WIDTH - mapPadding - Constants.UNIT_SIZE;
        int maxY = Constants.SCREEN_HEIGHT - mapPadding - Constants.UNIT_SIZE;

        do {
            invalid = false;
            int rangeX = (maxX - minPos) / Constants.UNIT_SIZE;
            int rangeY = (maxY - minPos) / Constants.UNIT_SIZE;

            if (rangeX <= 0 || rangeY <= 0) {
                fx = Constants.SCREEN_WIDTH / 2;
                fy = Constants.SCREEN_HEIGHT / 2;
            } else {
                fx = minPos + (random.nextInt(rangeX) * Constants.UNIT_SIZE);
                fy = minPos + (random.nextInt(rangeY) * Constants.UNIT_SIZE);
            }

            for (Point p : snake.getBody()) {
                if (p.getX() == fx && p.getY() == fy) { invalid = true; break; }
            }
            for (Obstacle ob : obstacles) {
                if (ob.getX() == fx && ob.getY() == fy) { invalid = true; break; }
            }
            
        } while (invalid);
        
        return new Point(fx, fy);
    }

    private void addObstacles() {

        int fx = random.nextInt(Constants.SCREEN_WIDTH / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
        int fy = random.nextInt(Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
        
        boolean overlap = false;
        for (Point p : snake.getBody()) {
            if (p.getX() == fx && p.getY() == fy) { overlap = true; break; }
        }
        
        if (!overlap) {
            obstacles.add(new Obstacle(fx, fy));
        }
    }

    private void gameOver() {
        running = false;
        timer.stop();

        if (score > highScoreClassic) {
            highScoreClassic = score;
            fileManager.saveHighScore(highScoreClassic, "highscore_classic.txt");
        }

        if (view.vibrateOn) {
            vibrateFrame();
        }

        handleHighScore(); 
        view.repaint();
    }

    private void startCountdown() {
        view.showSettings = false;
        countdownValue = 3;
        
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
        
        countdownTimer = new javax.swing.Timer(1000, e -> {
            countdownValue--;
            if (countdownValue <= 0) {
                countdownTimer.stop();
                isPaused = false;
            }
            view.repaint();
        });
        countdownTimer.start();
    }

    private void vibrateFrame() {
        Window window = SwingUtilities.getWindowAncestor(view);
        if (window == null) return;
        java.awt.Point oldLoc = window.getLocation();
        for (int i = 0; i < 10; i++) {
            window.setLocation(new java.awt.Point(oldLoc.x + (i % 2 == 0 ? 5 : -5), oldLoc.y));
            try { Thread.sleep(20); } catch (InterruptedException e) {}
        }
        window.setLocation(oldLoc);
    }

    // --- XỬ LÝ CLICK CHUỘT ---
    public void handleMouseClick(int x, int y) {
        // 1. Nút Bánh răng (Settings) trên HUD
        if (x >= Constants.SCREEN_WIDTH - 60 && y <= 60) {
            if (!view.showSettings) {
                view.showSettings = true;
                isPaused = true;
            } else {
                startCountdown();
            }
        }

        // 2. Trong bảng Settings
        if (view.showSettings) {
            if (y >= 235 && y <= 260) { // Sound
                if (x >= 300 && x <= 340) view.soundOn = true;
                if (x >= 350 && x <= 390) view.soundOn = false;
            }
            if (y >= 295 && y <= 320) { // Vibrate
                if (x >= 300 && x <= 340) view.vibrateOn = true;
                if (x >= 350 && x <= 390) view.vibrateOn = false;
            }
            if (y >= 355 && y <= 380) { // Mode
                if (x >= 260 && x <= 330) view.isDarkMode = true;
                if (x >= 340 && x <= 410) view.isDarkMode = false;
            }
            if (x >= 240 && x <= 340 && y >= 440 && y <= 480) {
                startCountdown();
            }
        } 
        // 3. Reset game khi thua
        else if (!running) {
            initGame();
        }

        // 4. Menu
        if (inMenu) {
            int btnWidth = 250;
            int btnHeight = 50;
            int btnX = (Constants.SCREEN_WIDTH - btnWidth) / 2;
            int btnY = 300;

            // Kiểm tra Click nút CLASSIC MODE (y = 250)
            if (x >= btnX && x <= btnX + btnWidth && y >= btnY && y <= btnY + btnHeight) {
                startClassicMode();
            }
            

            return;
        }

        if (!inMenu) {
            if (x >= 160 && x <= 200 && y >= 30 && y <= 70) {
                returnToMenu();
                return; // Thoát hàm ngay sau khi xử lý xong
            }

            int settingsX = Constants.SCREEN_WIDTH - 60;
            if (x >= settingsX && x <= settingsX + 40 && y >= 30 && y <= 70) {
                isPaused = true;
                view.showSettings = true; // Hiện bảng Settings (nếu bạn đã làm)
                return;
            }
        }

    }

    private void returnToMenu() {
        inMenu = true;
        running = false; // Dừng game
        if (timer != null) timer.stop();
        mapPadding = 0;
        view.repaint();
    }

    public void startClassicMode() {
        this.currentMode = GameMode.CLASSIC; 
        this.inMenu = false;
        this.mapPadding = 0;
        initGame();
    }

    public void startModernMode() {
        this.currentMode = GameMode.MODERN; 
        this.inMenu = false;
        initGame(); 
    }

    private void generateObstacles() {
        if (this.currentMode != GameMode.MODERN) return;
        obstacles.clear();
        for (int i = 0; i < 5; i++) {
            int ox = random.nextInt(Constants.SCREEN_WIDTH / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
            int oy = random.nextInt(Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
            obstacles.add(new Obstacle(ox, oy));
        }
    }

    private void generateBomb() {
        int bx = random.nextInt(Constants.SCREEN_WIDTH / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
        int by = random.nextInt(Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
        Point head = snake.getBody().get(0);
        if (bx == head.getX() && by == head.getY()) {
            generateBomb(); // Thử lại nếu trùng
            return;
        }
        bomb = new Bomb(bx, by);

    }


    public void processKey(int keyCode) {
        if (keyCode == KeyEvent.VK_R) initGame();
        if (keyCode == KeyEvent.VK_P) isPaused = !isPaused;
        if (!isPaused && running) {
            switch (keyCode) {
                case KeyEvent.VK_LEFT -> { if (snake.getDirection() != 'R') snake.setDirection('L'); }
                case KeyEvent.VK_RIGHT -> { if (snake.getDirection() != 'L') snake.setDirection('R'); }
                case KeyEvent.VK_UP -> { if (snake.getDirection() != 'D') snake.setDirection('U'); }
                case KeyEvent.VK_DOWN -> { if (snake.getDirection() != 'U') snake.setDirection('D'); }
            }
        }
    }
}