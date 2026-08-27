application.properties 
spring.application.name=10-order-app-rag-db

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=root

# OpenAI
spring.ai.openai.api-key= {your key}

# PGVector
spring.ai.vectorstore.pgvector.initialize-schema=true
spring.ai.vectorstore.pgvector.index-type=HNSW
spring.ai.vectorstore.pgvector.table-name=vector_store_orders
spring.ai.vectorstore.pgvector.distance-type=COSINE_DISTANCE
spring.ai.vectorstore.pgvector.dimensions=1536
spring.ai.openai.embedding.options.model=text-embedding-3-small

--------------------
create tables in post gres
--------------------------
CREATE TABLE orders (    id BIGSERIAL PRIMARY KEY,    order_number VARCHAR(50) UNIQUE NOT NULL,    customer_name VARCHAR(100) NOT NULL,    product_name VARCHAR(200) NOT NULL,    quantity INTEGER NOT NULL,    order_status VARCHAR(50) NOT NULL,    order_date DATE NOT NULL,    expected_delivery_date DATE,    shipping_address VARCHAR(300)
);

INSERT INTO orders
(order_number, customer_name, product_name, quantity,
 order_status, order_date, expected_delivery_date, shipping_address)
VALUES
('ORD1001', 'Albin', 'Dell Laptop', 1,
 'SHIPPED', '2026-08-20', '2026-08-29',
 'Bangalore'),

('ORD1002', 'Rahul', 'Samsung Monitor', 2,
 'DELIVERED', '2026-08-18', '2026-08-23',
 'Chennai'),

('ORD1003', 'Priya', 'iPhone 17', 1,
 'OUT_FOR_DELIVERY', '2026-08-24', '2026-08-27',
 'Bangalore'),

('ORD1004', 'Arun', 'Sony Headphones', 1,
 'PROCESSING', '2026-08-26', '2026-09-01',
 'Hyderabad'),

('ORD1005', 'Meena', 'HP Laptop', 1,
 'CANCELLED', '2026-08-21', NULL,
 'Coimbatore'),

('ORD1006', 'Vijay', 'Logitech Keyboard', 3,
 'SHIPPED', '2026-08-22', '2026-08-30',
 'Bangalore'),

('ORD1007', 'Karthik', 'OnePlus Phone', 1,
 'OUT_FOR_DELIVERY', '2026-08-25', '2026-08-27',
 'Mysore'),

('ORD1008', 'Divya', 'Apple AirPods', 2,
 'DELIVERED', '2026-08-15', '2026-08-20',
 'Bangalore');

