package com.mohamed.tahiri.backend.conversation;

public class ConversationTitle {
    public Long id;
    public String fullName;
    public String lastMessage;
    public String time;
    public String image;
    public Long admin;

    public ConversationTitle(Long id, String fullName, String lastMessage, String time, String image, Long admin) {
        this.id = id;
        this.fullName = fullName;
        this.lastMessage = lastMessage;
        this.time = time;
        this.image = image;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
