package com.mohamed.tahiri.backend.message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Collection<? extends Message> getAllByConversationId(Long conversationId);

}
