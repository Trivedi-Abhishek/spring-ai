package com.spring.openai.controller;

import com.spring.openai.entity.HelpDeskTicket;
import com.spring.openai.model.TicketRequest;
import com.spring.openai.tools.HelpDeskTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/tool-calling")
public class HelpDeskController {

    private final ChatClient chatClient;
    private final HelpDeskTools helpDeskTools;

    public HelpDeskController(@Qualifier("helpDeskChatClient") ChatClient chatClient, HelpDeskTools helpDeskTools) {
        this.chatClient=chatClient;
        this.helpDeskTools=helpDeskTools;
    }

    @GetMapping("/help-desk")
    public ResponseEntity<String> getTickets(@RequestParam("message") String message, @RequestHeader("username") String username) {

        return ResponseEntity.ok(chatClient.prompt().user(message).advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                .tools(helpDeskTools).toolContext(Map.of("username", username)).call().content());
    }
}
