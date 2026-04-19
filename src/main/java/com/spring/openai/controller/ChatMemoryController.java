package com.spring.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api")
public class ChatMemoryController {

    private final ChatClient chatClient;

    public ChatMemoryController(@Qualifier("memoryChatClientConfig") ChatClient chatClient) {
        this.chatClient=chatClient;
    }

    @GetMapping("/chat-memory")
    public String chatMemory(@RequestHeader("username") String username, @RequestParam("message") String message) {

        return chatClient.prompt().advisors(advisorSpec -> {
            advisorSpec.param(CONVERSATION_ID, username);
        }).user(message).call().content();
    }
}
