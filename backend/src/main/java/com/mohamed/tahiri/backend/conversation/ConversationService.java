package com.mohamed.tahiri.backend.conversation;

import com.mohamed.tahiri.backend.message.Message;
import com.mohamed.tahiri.backend.message.MessageService;
import com.mohamed.tahiri.backend.user.User;
import com.mohamed.tahiri.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id).get();
    }

    public Conversation createConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    public List<ConversationTitle> allConversations(Long userid) {
        ArrayList<Conversation> conversations = new ArrayList<>();
        conversations.addAll(conversationRepository.getAllByCreatorId(userid));
        conversations.addAll(conversationRepository.getAllByParticipantId(userid));

        ArrayList<ConversationTitle> conversationTitle = new ArrayList<>();
        for (int i = 0; i < conversations.size(); i++) {
            Long id = conversations.get(i).getId();
            String fullName = "";
            if (conversations.get(i).getCreatorId() == userid) {
                fullName = userRepository.findById(conversations.get(i).getParticipantId())
                        .orElse(new User()) // Provide a default User object
                        .getFullName();
            } else if (conversations.get(i).getParticipantId() == userid) {
                fullName = userRepository.findById(conversations.get(i).getCreatorId())
                        .orElse(new User()) // Provide a default User object
                        .getFullName();
            }

            List<Message> messages = messageService.allMessages(id);
            String lastMessage = messages.isEmpty() ? "No messages" : messages.getLast().getContent();
            String time = messages.isEmpty() ? "" : messages.getLast().getDateSending();

            ConversationTitle ct = new ConversationTitle(id, fullName, lastMessage, time);
            conversationTitle.add(ct);
        }

        return conversationTitle;
    }
}
