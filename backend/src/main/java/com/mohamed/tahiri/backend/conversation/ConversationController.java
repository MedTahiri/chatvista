package com.mohamed.tahiri.backend.conversation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    @GetMapping("/{id}")
    public Conversation getConversationById(@PathVariable("id") Long id) {
        return conversationService.getConversationById(id);
    }

    @PostMapping("/new")
    public ConversationTitle newConversation(@RequestBody Conversation conversation) {
        return conversationService.createConversation(conversation);
    }

    @GetMapping("/my/{userid}")
    public List<ConversationTitle> allConversations(@PathVariable("userid") Long userid) {
        return conversationService.allConversations(userid);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteConversation(@PathVariable("id") Long id) {
        conversationService.deleteConversation(id);
    }

}
