package com.spring.openai.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HRDataLoader {

    private final VectorStore vectorStore;

    public HRDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Value("classpath:/Eazybytes_HR_Policies.pdf")
    Resource hrDocument;

    @PostConstruct
    public void loadHrDocuments() {
        TikaDocumentReader tikaDocumentReader=new TikaDocumentReader(hrDocument);
        List<Document> documentList = tikaDocumentReader.get();
//        vectorStore.add(documentList);

        //To reduce number of prompt tokens-> dividing large document into smaller chunks/docs
        TextSplitter textSplitter = TokenTextSplitter.builder().withChunkSize(200).withMaxNumChunks(400).build();
        vectorStore.add(textSplitter.split(documentList));

    }
}
