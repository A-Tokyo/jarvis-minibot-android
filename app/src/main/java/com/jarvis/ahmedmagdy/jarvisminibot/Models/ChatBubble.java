package com.jarvis.ahmedmagdy.jarvisminibot.Models;

/**
 * Created by ahmedmagdy on 11/29/16.
 */

public class ChatBubble {
    private String message;
    private boolean left;

    public ChatBubble(String message, boolean left) {
        this.message = message;
        this.left = left;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
