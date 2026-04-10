package com.snakegame.view;

import com.snakegame.utils.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private JButton startButton;

    public MenuPanel(ActionListener startAction) {
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(new Color(20, 20, 20));
        this.setLayout(new GridBagLayout()); // Căn giữa các nút

        JLabel title = new JLabel("SNAKE GAME NÂNG CẤP");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.GREEN);

        startButton = new JButton("BẮT ĐẦU CHƠI");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setFocusable(false);
        startButton.addActionListener(startAction);

        // Bố cục các thành phần
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(title, gbc);

        gbc.gridy = 1;
        add(startButton, gbc);
    }
}