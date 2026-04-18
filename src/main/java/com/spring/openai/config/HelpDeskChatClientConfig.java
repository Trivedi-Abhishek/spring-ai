package com.spring.openai.config;

import com.spring.openai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class HelpDeskChatClientConfig {

    @Value("classpath:/promptTemplates/helpDeskPromptTemplate.st")
    Resource helpDeskPromptTemplate;

    @Bean("helpDeskChatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {

        Advisor loggerAdvisor=new SimpleLoggerAdvisor();
        Advisor tokenUsageAuditAdvisor = new TokenUsageAuditAdvisor();
        Advisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder.defaultSystem(helpDeskPromptTemplate)
                .defaultAdvisors(List.of(loggerAdvisor, tokenUsageAuditAdvisor, messageChatMemoryAdvisor))
                .build();
    }
}
