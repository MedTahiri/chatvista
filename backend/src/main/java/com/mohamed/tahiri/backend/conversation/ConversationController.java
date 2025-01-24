package com.mohamed.tahiri.backend.conversation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Conversation newConversation(@RequestBody Conversation conversation) {
        return conversationService.createConversation(conversation);
    }

}
