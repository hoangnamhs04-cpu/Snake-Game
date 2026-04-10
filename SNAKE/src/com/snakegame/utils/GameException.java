package com.snakegame.utils;

/**
 * Custom Exception để xử lý các lỗi riêng biệt của Game
 */
public class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }
}