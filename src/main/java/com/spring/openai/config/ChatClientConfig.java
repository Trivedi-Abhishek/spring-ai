package com.spring.openai.config;

import com.spring.openai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {

        return chatClientBuilder
                .defaultAdvisors(List.of(new TokenUsageAuditAdvisor(), new SimpleLoggerAdvisor()))
                .defaultSystem("Act like an HR assistant and only answer questions related to company policies and employees")
                .defaultUser("How can you help me?")
                .build();
    }
}
