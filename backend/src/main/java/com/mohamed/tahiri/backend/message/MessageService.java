package com.mohamed.tahiri.backend.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message newMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> allMessages(Long conversationId) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.addAll(messageRepository.getAllByConversationId(conversationId));
        return messages;
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
