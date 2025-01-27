package com.example.GraduationProject.modules;

public class chatbotpatient {

    private String message;
    private boolean isUser; // True if the message is from the user, false if from the bot

    public chatbotpatient(String message, boolean isUser) {
        this.message = message;
        this.isUser = isUser;
    }

    public String getMessage() {
        return message;
    }

    public boolean isUser() {
        return isUser;
    }
}