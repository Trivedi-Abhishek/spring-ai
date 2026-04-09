package com.spring.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptStuffingController {

    private final ChatClient chatClient;

    public PromptStuffingController(ChatClient.Builder chatClientBuilder) {
        chatClient=chatClientBuilder.build();
    }

    @Value("classpath:/promptTemplates/promptStuffingTemplate.st")
    Resource promptStuffingTemplate;

    @GetMapping("/prompt-stuffing")
    public String promptStuffing(@RequestParam("message") String message) {

        return chatClient.prompt().system(promptStuffingTemplate).user(message).call().content();
    }

}
