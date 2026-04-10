package com.snakegame.view;

import javax.swing.JFrame;

/**
 * Lớp này kế thừa từ JFrame để tạo cửa sổ chính cho Game.
 * Thuộc tầng View trong mô hình MVC.
 */
public class GameFrame extends JFrame {

    public GameFrame(GamePanel panel) {
        // Thiết lập các thuộc tính cho cửa sổ
        this.setTitle("Snake Game - Nâng cấp (MVC)"); // Tiêu đề game
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng ứng dụng khi nhấn X
        this.setResizable(false); // Không cho phép người dùng thay đổi kích thước cửa sổ
        
        // Thêm vùng vẽ Game vào khung
        this.add(panel);
        
        // Tự động điều chỉnh kích thước khung vừa vặn với Panel bên trong
        this.pack();
        
        // Hiển thị cửa sổ ở chính giữa màn hình
        this.setLocationRelativeTo(null);
        
        // Làm cho cửa sổ xuất hiện
        this.setVisible(true);
    }
}