package com.spring.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/tool-calling")
public class TimeController {

    private final ChatClient chatClient;

    public TimeController(@Qualifier("timeChatClientConfig") ChatClient chatClient) {
        this.chatClient=chatClient;
    }

    @GetMapping("/time")
    public ResponseEntity<String> getTime(@RequestHeader("username") String username, @RequestParam("message") String message) {

        return ResponseEntity.ok(chatClient.prompt().advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username)).user(message).call().content());
    }
}
