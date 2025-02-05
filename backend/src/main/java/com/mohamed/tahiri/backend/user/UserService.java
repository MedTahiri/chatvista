package com.mohamed.tahiri.backend.user;

import com.mohamed.tahiri.backend.conversation.Conversation;
import com.mohamed.tahiri.backend.conversation.ConversationRepository;
import com.mohamed.tahiri.backend.message.Message;
import com.mohamed.tahiri.backend.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    public User getUser(String email, String password) {
        for (int i = 0; i < allUsers().size(); i++) {
            if (allUsers().get(i).getEmail().equals(email) && allUsers().get(i).getPassword().equals(password)) {
                return allUsers().get(i);
            }
        }
        return new User();
    }

    public User newUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updateUser) {
        User user = getUserById(updateUser.getId());
        user.setFullName(updateUser.getFullName());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        user.setImage(updateUser.getImage());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        ArrayList<Conversation> conversations = new ArrayList<>();
        conversations.addAll(conversationRepository.getAllByCreatorId(id));
        conversations.addAll(conversationRepository.getAllByParticipantId(id));
        for (Conversation conversation : conversations) {
            ArrayList<Message> messages = new ArrayList<>();
            messages.addAll(messageRepository.getAllByConversationId(conversation.getId()));
            for (Message message : messages) {
                messageRepository.deleteById(message.getId());
            }
        }
        for (Conversation conversation : conversations) {
            conversationRepository.deleteById(conversation.getId());
        }
        userRepository.deleteById(id);
    }

    public List<User> findUsers(String text) {
        return userRepository.findAllByFullNameContainsOrEmailContains(text, text);
    }
}
