package com.mohamed.tahiri.backend.conversation;

public class ConversationTitle {
    public Long id;
    public String fullName;
    public String lastMessage;
    public String time;

    public ConversationTitle(Long id, String fullName, String lastMessage, String time) {
        this.id = id;
        this.fullName = fullName;
        this.lastMessage = lastMessage;
        this.time = time;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
