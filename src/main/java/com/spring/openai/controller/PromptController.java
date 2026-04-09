package com.spring.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptController {

    private final ChatClient chatClient;

    public PromptController(ChatClient.Builder chatClientBuilder) {
        chatClient=chatClientBuilder.build();
    }

//    String promptTemplate= "employee with name:{customerName} queried about {customerMessage} respond to this query in a professional way";

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource promptTemplate;

    @GetMapping("/email")
    public ResponseEntity<String> emailPrompt(@RequestParam("customerName") String customerName,
                                              @RequestParam("customerMessage") String customerMessage) {

        return ResponseEntity.ok(chatClient.prompt().advisors(new SimpleLoggerAdvisor()).system("You are IT team mail assistant respond to the queries, " +
                "only concerns related to IT support, by drafting a mail, only provide mail body.").user(promptTemplateSpec-> promptTemplateSpec.text(promptTemplate).param("customerName", customerName)
                .param("customerMessage", customerMessage)).call().content());
    }

}
