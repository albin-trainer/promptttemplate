package com.example.config;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class OrderRagConfig {

    private final JdbcTemplate jdbcTemplate;
    private final VectorStore vectorStore;

    public OrderRagConfig(
            JdbcTemplate jdbcTemplate,     @Qualifier("orderVectorStore")
            VectorStore vectorStore) {

        this.jdbcTemplate = jdbcTemplate;
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadOrders() {

        System.out.println("Loading orders from PostgreSQL...");

        List<Document> documents = jdbcTemplate.query(
                "SELECT * FROM orders",
                (rs, rowNum) -> {

                    String content = """
                            Order Number: %s
                            Customer: %s
                            Product: %s
                            Quantity: %d
                            Order Status: %s
                            Order Date: %s
                            Expected Delivery Date: %s
                            Shipping Address: %s
                            """.formatted(
                            rs.getString("order_number"),
                            rs.getString("customer_name"),
                            rs.getString("product_name"),
                            rs.getInt("quantity"),
                            rs.getString("order_status"),
                            rs.getDate("order_date"),
                            rs.getDate("expected_delivery_date"),
                            rs.getString("shipping_address")
                    );

                    return new Document(
                            content,
                            Map.of(
                                    "orderNumber",
                                    rs.getString("order_number"),
                                    "customerName",
                                    rs.getString("customer_name"),
                                    "status",
                                    rs.getString("order_status")
                            )
                    );
                }
        );

        System.out.println(
                "Orders found: " + documents.size()
        );

        vectorStore.add(documents);

        System.out.println(
                "Orders embedded and stored in PGVector."
        );
    }
}