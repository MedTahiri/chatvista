package com.mohamed.tahiri.backend.conversation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Conversation> allConversations(Long userid) {
        ArrayList<Conversation> conversations = new ArrayList<>();
        conversations.addAll(conversationRepository.getAllByCreatorId(userid));
        conversations.addAll(conversationRepository.getAllByParticipantId(userid));
        return conversations;
    }
}
