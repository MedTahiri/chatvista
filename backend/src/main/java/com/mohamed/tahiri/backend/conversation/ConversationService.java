package com.mohamed.tahiri.backend.conversation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id).get();
    }

    public Conversation createConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }
}
