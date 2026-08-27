package com.example.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class OrderVectorStoreConfig {

    @Bean
    public VectorStore orderVectorStore(
            JdbcTemplate jdbcTemplate,
            EmbeddingModel embeddingModel) {

        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .vectorTableName("vector_store_orders")
                .build();
    }
}