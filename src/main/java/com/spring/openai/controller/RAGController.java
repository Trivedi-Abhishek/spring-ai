package com.spring.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/rag")
public class RAGController {

    private final ChatClient chatClient;
    private final ChatClient webSearchRagChatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/promptTemplates/ragInputTemplate.st")
    Resource ragTemplate;

    public RAGController(@Qualifier("memoryChatClientConfig") ChatClient chatClient, @Qualifier("webSearchRAGChatClient")
    ChatClient webSearchRAGChatClient, VectorStore vectorStore) {

        this.chatClient=chatClient;
        this.webSearchRagChatClient=webSearchRAGChatClient;
        this.vectorStore=vectorStore;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chatRag(@RequestHeader("username") String username,
                                          @RequestParam("message") String message) {

//        SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).similarityThreshold(0.5).build();
//        List<Document> documents = vectorStore.similaritySearch(searchRequest);
//
//        String similarContext = documents.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));

        return ResponseEntity.ok(chatClient.prompt()
//                .system(promptSystemSpec -> {
//            promptSystemSpec.text(ragTemplate).param("document", similarContext);})
                .advisors(advisorSpec -> {
            advisorSpec.param(CONVERSATION_ID, username);
        }).user(message).call().content());

    }

    @GetMapping("/document/chat")
    public ResponseEntity<String> chatDocumentRag(@RequestHeader("username") String username,
                                          @RequestParam("message") String message) {
//        SearchRequest searchRequest= SearchRequest.builder().query(message).topK(3).similarityThreshold(0.4).build();
//        List<Document> documentList = vectorStore.similaritySearch(searchRequest);
//
//        String context = documentList.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));

        return ResponseEntity.ok(chatClient.prompt().user(message)
//                .system(systemSpec -> {
//            systemSpec.text(ragTemplate).param("document", context);})
                .advisors(advisorSpec -> {
            advisorSpec.param(CONVERSATION_ID, username);
        }).user(message).call().content());
    }

    @GetMapping("/web-search/chat")
    public ResponseEntity<String> webSearchChat(@RequestHeader("username") String username,
                                                  @RequestParam("message") String message) {
        return ResponseEntity.ok(webSearchRagChatClient.prompt().advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                .user(message).call().content());
    }

}
