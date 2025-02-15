package com.mohamed.tahiri.backend.message;

import com.mohamed.tahiri.backend.conversation.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/new")
    public Message newMessage(@RequestBody Message message) {
        return messageService.newMessage(message);
    }

    @GetMapping("/my/{conversationId}")
    public List<Message> allMessages(@PathVariable("conversationId") Long conversationId) {
        return messageService.allMessages(conversationId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMessage(@PathVariable("id") Long id) {
        messageService.deleteMessage(id);
    }

    @PutMapping("/read/{conversation}/{currentuser}")
    public void readMessage(@PathVariable("conversation") Long conversationId,@PathVariable("currentuser") Long currentUser) {
        messageService.readMessage(conversationId,currentUser);
    }

}
