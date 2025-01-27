package com.mohamed.tahiri.backend.conversation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> getAllByCreatorId(Long userid);

    List<Conversation> getAllByParticipantId(Long userid);

}
