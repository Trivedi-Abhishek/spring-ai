package com.spring.openai.config;

import com.spring.openai.advisor.TokenUsageAuditAdvisor;
import com.spring.openai.rag.WebSearchDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class WebSearchRAGChatClientConfig {

    @Bean("webSearchRAGChatClient")
    public ChatClient webSearchRAGChatClientConfig(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, RestClient.Builder restClientBuilder) {

        SimpleLoggerAdvisor simpleLoggerAdvisor=new SimpleLoggerAdvisor();
        TokenUsageAuditAdvisor tokenUsageAuditAdvisor=new TokenUsageAuditAdvisor();
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        RetrievalAugmentationAdvisor documentRetriever = RetrievalAugmentationAdvisor.builder().documentRetriever(WebSearchDocumentRetriever.builder()
                .restClientBuilder(restClientBuilder).maxResults(5).build()).build();
        return chatClientBuilder.defaultAdvisors(List.of(simpleLoggerAdvisor, tokenUsageAuditAdvisor, messageChatMemoryAdvisor, documentRetriever)).build();
    }
}
