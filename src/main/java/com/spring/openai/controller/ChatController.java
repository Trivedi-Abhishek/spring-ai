package com.spring.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
//        return chatClient.prompt(message).call().content();

        return chatClient.prompt()
                .system("Act like an IT team assistant and only answer questions related to IT stuff only") //-> if provided overrides default system
                .user(message) //-> if provided overrides default system
                .call().content();
    }
}
