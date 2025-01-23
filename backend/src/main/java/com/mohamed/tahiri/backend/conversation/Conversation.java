package com.mohamed.tahiri.backend.conversation;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long person1Id;
    public Long person2Id;

    public List<Long> messagesId;
}
