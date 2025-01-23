package com.mohamed.tahiri.backend.message;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String content;
    public Date dateSending;
    public Long senderId;
    public Long recipientId;

    public long conversationId;

}
