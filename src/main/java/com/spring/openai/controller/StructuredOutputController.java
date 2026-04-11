package com.spring.openai.controller;

import com.spring.openai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
        this.chatClient=chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }

    @GetMapping("/chat-structured")
    public ResponseEntity<CountryCities> chatStructured(@RequestParam("message") String message) {

//        CountryCities response = chatClient.prompt().user(message).call().entity(CountryCities.class);
        CountryCities response = chatClient.prompt().user(message).call().entity(new BeanOutputConverter<>(CountryCities.class));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> chatList(@RequestParam("message") String message) {

        List<String> response = chatClient.prompt().user(message).call().entity(new ListOutputConverter());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/chat-map")
    public ResponseEntity<Map<String, Object>> chatMap(@RequestParam("message") String message) {

        Map<String, Object> response = chatClient.prompt().user(message).call().entity(new MapOutputConverter());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/chat-bean-list")
    public ResponseEntity<List<CountryCities>> chatBeanList(@RequestParam("message") String message) {

        List<CountryCities> response = chatClient.prompt().user(message).call().entity(new ParameterizedTypeReference<List<CountryCities>>() {
        });
        return ResponseEntity.ok(response);
    }
}
