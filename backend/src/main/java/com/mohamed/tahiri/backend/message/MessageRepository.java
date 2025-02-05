package com.mohamed.tahiri.backend.message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Collection<? extends Message> getAllByConversationId(Long conversationId);

    Collection<? extends Message> findAllByContentContains(String content);

    List<Message> getAllByConversationIdAndIsRead(Long conversationId, Boolean isRead);
}
