package com.spring.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {

        return chatClientBuilder.
                defaultSystem("Act like an HR assistant and only answer questions related to company policies and employees")
                .defaultUser("How can you help me?")
                .build();
    }
}
