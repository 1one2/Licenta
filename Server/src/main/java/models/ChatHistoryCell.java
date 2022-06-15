package models;

import java.awt.*;

public class ChatHistoryCell {

    private int chatId;
    private String name;
    private Image profilePicture;

    //Empty constructor
    public ChatHistoryCell() {
    }

    //Constructor without the image property
    public ChatHistoryCell(int chatId, String name) {
        this.chatId = chatId;
        this.name = name;
    }

    //Full constructor
    public ChatHistoryCell(int chatId, String name, Image profilePicture) {
        this.chatId = chatId;
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }
}
