package com.example.hr_ai.config;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import jakarta.annotation.PostConstruct;

@Configuration
public class HRConfig {

    @Value("classpath:/hrpolicy.txt")
    private Resource hrPolicy;

    private final VectorStore vectorStore;

    public HRConfig(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadData() {

        System.out.println("Loading HR Policy...");

        // 1. Read HR policy
        TextReader textReader = new TextReader(hrPolicy);

        textReader.getCustomMetadata()
                .put("filename", "hrpolicy.txt");

        List<Document> documents = textReader.get();

        // 2. Split into chunks
        TextSplitter textSplitter = new TokenTextSplitter(
                50,
                20,
                5,
                1000,
                true,
                List.of('.', '?', '!', ';', ':', '\n')
        );

        List<Document> splitDocuments =
                textSplitter.apply(documents);

        System.out.println(
                "Number of chunks: " + splitDocuments.size()
        );

        // 3. Generate embeddings + store in PGVector
        vectorStore.add(splitDocuments);

        System.out.println(
                "HR Policy stored in PostgreSQL using PGVector!"
        );
    }
}