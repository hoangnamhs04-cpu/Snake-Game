package com.snakegame.utils;

import java.io.*;
import java.util.Scanner;

public class FileManager {

    // 1. Hàm tải điểm cao từ một file cụ thể
    public int loadHighScore(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return 0; // Nếu file chưa tồn tại (lần đầu chơi), điểm cao là 0
        }

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy file: " + fileName);
        }
        return 0;
    }

    // 2. Hàm lưu điểm cao vào một file cụ thể (Đã thêm tham số fileName)
    public void saveHighScore(int score, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.print(score);
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu điểm cao vào file: " + fileName);
            e.printStackTrace();
        }
    }
}