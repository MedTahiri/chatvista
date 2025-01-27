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
    private LocalDateTime dateSending;
    private Long senderId;
    private Long conversationId;

    public Message() {

    }

    public Message(String content, Long senderId, Long conversationId) {
        this.content = content;
        this.dateSending = LocalDateTime.now();
        this.senderId = senderId;
        this.conversationId = conversationId;
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

    public LocalDateTime getDateSending() {
        return dateSending;
    }

    public void setDateSending(LocalDateTime dateSending) {
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
}
