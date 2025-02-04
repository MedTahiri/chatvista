package com.mohamed.tahiri.backend.conversation;

import com.mohamed.tahiri.backend.message.Message;
import com.mohamed.tahiri.backend.message.MessageRepository;
import com.mohamed.tahiri.backend.user.User;
import com.mohamed.tahiri.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id).get();
    }
/*
    public ConversationTitle createConversation(Conversation conversation) {
        List<Conversation> conversations = new ArrayList<>();
        conversations.addAll(conversationRepository.getAllByCreatorIdAndParticipantId(conversation.getCreatorId(), conversation.getParticipantId()));
        conversations.addAll(conversationRepository.getAllByCreatorIdAndParticipantId(conversation.getParticipantId(), conversation.getCreatorId()));

        if (!conversations.isEmpty() || conversation.getCreatorId() == conversation.getParticipantId()) {
            //return conversations.getFirst();
            return new ConversationTitle(conversations.getFirst().getId(),
                    userRepository.findById(conversations.getFirst().getParticipantId()).get().getFullName(),
                    messageService.allMessages(conversations.getFirst().getId()).getLast().getDateSending(),
                    messageService.allMessages(conversations.getFirst().getId()).getLast().getContent(),
                    userRepository.findById(conversations.getFirst().getParticipantId()).get().getImage()
            );
        }

        //return conversationRepository.save(conversation);
        return  new ConversationTitle(
                conversationRepository.save(conversation).getId(),
                userRepository.findById(conversationRepository.save(conversation).getParticipantId()).get().getFullName(),
                messageService.allMessages(conversationRepository.save(conversation).getId()).getLast().getDateSending(),
                messageService.allMessages(conversationRepository.save(conversation).getId()).getLast().getContent(),
                userRepository.findById(conversationRepository.save(conversation).getParticipantId()).get().getImage()
        );

    }


 */

    public ConversationTitle createConversation(Conversation conversation) {
        // Check if a conversation already exists between the creator and participant
        List<Conversation> existingConversations = conversationRepository.getAllByCreatorIdAndParticipantId(conversation.getCreatorId(), conversation.getParticipantId());
        existingConversations.addAll(conversationRepository.getAllByCreatorIdAndParticipantId(conversation.getParticipantId(), conversation.getCreatorId()));

        if (!existingConversations.isEmpty() || conversation.getCreatorId().equals(conversation.getParticipantId())) {
            Conversation existingConversation = existingConversations.getFirst();
            User participant = userRepository.findById(existingConversation.getParticipantId())
                    .orElseThrow(() -> new RuntimeException("Participant not found"));
            ArrayList<Message> messages = new ArrayList<>();
            messages.addAll(messageRepository.getAllByConversationId(existingConversation.getId()));
            Message lastMessage = messages.isEmpty() ? null : messages.getLast();

            return new ConversationTitle(
                    existingConversation.getId(),
                    participant.getFullName(),
                    lastMessage != null ? lastMessage.getDateSending() : "1970-01-01 00:00:00",
                    lastMessage != null ? lastMessage.getContent() : "No messages",
                    participant.getImage()
            );
        }

        Conversation savedConversation = conversationRepository.save(conversation);
        User participant = userRepository.findById(savedConversation.getParticipantId())
                .orElseThrow(() -> new RuntimeException("Participant not found"));
        return new ConversationTitle(
                savedConversation.getId(),
                participant.getFullName(),
                "1970-01-01 00:00:00",
                "No messages",
                participant.getImage()
        );
    }

    public List<ConversationTitle> allConversations(Long userid) {
        ArrayList<Conversation> conversations = new ArrayList<>();
        conversations.addAll(conversationRepository.getAllByCreatorId(userid));
        conversations.addAll(conversationRepository.getAllByParticipantId(userid));

        ArrayList<ConversationTitle> conversationTitle = new ArrayList<>();
        for (int i = 0; i < conversations.size(); i++) {
            Long id = conversations.get(i).getId();
            String fullName = "";
            String image = "0";
            if (conversations.get(i).getCreatorId() == userid) {
                fullName = userRepository.findById(conversations.get(i).getParticipantId())
                        .orElse(new User())
                        .getFullName();
                image = userRepository.findById(conversations.get(i).getParticipantId()).orElse(new User()).getImage();
            } else if (conversations.get(i).getParticipantId() == userid) {
                fullName = userRepository.findById(conversations.get(i).getCreatorId())
                        .orElse(new User())
                        .getFullName();
                image = userRepository.findById(conversations.get(i).getCreatorId()).orElse(new User()).getImage();
            }
            ArrayList<Message> messages = new ArrayList<>();
            messages.addAll(messageRepository.getAllByConversationId(id));
            String lastMessage = messages.isEmpty() ? "No messages" : messages.getLast().getContent();
            String time = messages.isEmpty() ? "1970-01-01 00:00:00" : messages.getLast().getDateSending();

            ConversationTitle ct = new ConversationTitle(id, fullName, lastMessage, time, image);
            conversationTitle.add(ct);

        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        conversationTitle.sort((ct1, ct2) -> {
            try {
                LocalDateTime time1 = LocalDateTime.parse(ct1.time, formatter);
                LocalDateTime time2 = LocalDateTime.parse(ct2.time, formatter);
                return time2.compareTo(time1);
            } catch (DateTimeParseException e) {
                return 0;
            }
        });


        return conversationTitle;
    }

    public void deleteConversation(Long id) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.addAll(messageRepository.getAllByConversationId(id));
        for (Message message : messages) {
            messageRepository.delete(message);
        }
        conversationRepository.deleteById(id);
    }
}
