package com.mohamed.tahiri.backend.message;

import com.mohamed.tahiri.backend.user.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private Date dateSending;
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiverUser;

    public Message() {
    }

    public Message(String content, Date dateSending, Boolean isRead, User senderUser, User receiverUser) {
        this.content = content;
        this.dateSending = dateSending;
        this.isRead = isRead;
        this.senderUser = senderUser;
        this.receiverUser = receiverUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateSending() {
        return dateSending;
    }

    public void setDateSending(Date dateSending) {
        this.dateSending = dateSending;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public User getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(User receiverUser) {
        this.receiverUser = receiverUser;
    }
}
