package com.mohamed.tahiri.backend.message;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String dateSending;
    private Long senderId;
    private Long conversationId;
    private Boolean isRead;

    public Message() {

    }

    public Message(String content, String dateSending, Long senderId, Long conversationId, Boolean isRead) {
        this.content = content;
        this.dateSending = dateSending;
        this.senderId = senderId;
        this.conversationId = conversationId;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateSending() {
        return dateSending;
    }

    public void setDateSending(String dateSending) {
        this.dateSending = dateSending;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Boolean getIsRead() {
        return isRead;
    }
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

}
