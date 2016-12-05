package com.jarvis.ahmedmagdy.jarvisminibot.Models;

/**
 * Created by ahmedmagdy on 11/29/16.
 */

public class ChatBubble {
    private String message;
    private boolean left;
    private boolean error;
    public ChatBubble(String message, boolean left, boolean error) {
        this.message = message;
        this.left = left;
        this.error= error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
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
