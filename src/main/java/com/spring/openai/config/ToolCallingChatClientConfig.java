package com.spring.openai.config;

import com.spring.openai.advisor.TokenUsageAuditAdvisor;
import com.spring.openai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ToolCallingChatClientConfig {

    @Bean("timeChatClientConfig")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, TimeTools timeTools) {

        Advisor loggerAdvisor=new SimpleLoggerAdvisor();
        Advisor tokenUsageAuditAdvisor = new TokenUsageAuditAdvisor();
        Advisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder.defaultTools(timeTools).defaultAdvisors(List.of(loggerAdvisor, tokenUsageAuditAdvisor, messageChatMemoryAdvisor)).build();
    }
}
