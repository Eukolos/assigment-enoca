CREATE TABLE IF NOT EXISTS customer
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP,
    version BIGINT DEFAULT 0,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS cart
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP,
    version BIGINT DEFAULT 0,
    customer_id BIGINT,
    total_price DECIMAL(19, 2),
    CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
    );

CREATE TABLE IF NOT EXISTS orders
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date  TIMESTAMP NOT NULL,
    update_date  TIMESTAMP,
    version BIGINT DEFAULT 0,
    order_code   VARCHAR(255),
    order_date   TIMESTAMP NOT NULL,
    customer_id  BIGINT,
    total_amount DECIMAL(19, 2),
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
    );

CREATE TABLE IF NOT EXISTS product
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date TIMESTAMP      NOT NULL,
    update_date TIMESTAMP,
    version BIGINT DEFAULT 0,
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    price       DECIMAL(19, 2) NOT NULL,
    stock       INT            NOT NULL
    );

CREATE TABLE IF NOT EXISTS product_holder
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP,
    version BIGINT DEFAULT 0,
    product_id  BIGINT,
    cart_id     BIGINT,
    order_id    BIGINT,
    quantity    INT       NOT NULL,
    price       DECIMAL(19, 2),
    CONSTRAINT fk_product_holder_product FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_product_holder_cart FOREIGN KEY (cart_id) REFERENCES cart (id),
    CONSTRAINT fk_product_holder_order FOREIGN KEY (order_id) REFERENCES orders (id)
);


