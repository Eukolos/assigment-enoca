package com.enoca.ecommerce.controller;

import com.enoca.ecommerce.AbstractIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
@Slf4j
class CartControllerTests extends AbstractIntegrationTest {
    @Test
    void shouldGetAllCartsWithCustomer() {
        given()
                .contentType("application/json")
                .body("""
                        {
                            "name": "string",
                            "email": "string"
                        }
                        """)
                .post("/api/v1/customers");

        given()
                .when().get("/api/v1/carts/customer")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldGetCartAfterCustomerCreated() {
        given()
                .contentType("application/json")
                .body("""
                        {
                            "name": "string",
                            "email": "string"
                        }
                        """)
                .post("/api/v1/customers");

        given()
                .when().get("/api/v1/carts/customer/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("customer.id", equalTo(1))
                .body("customer.name", equalTo("string"));
    }

    @Test
    void shouldAddProductToCart() {
        given()
                .contentType("application/json")
                .body("""
                        {
                            "name": "string",
                            "email": "string"
                        }
                        """)
                .post("/api/v1/customers");

        given()
                .contentType("application/json")
                .body("""
                        {
                         "name": "Elma",
                         "description": "Amasya Elması",
                         "price": 10,
                         "stock": 20
                       }
                        """)
                .post("/api/v1/products")
                .then()
                .statusCode(201);


        given()
                .contentType("application/json")
                .body("""
                        {
                            "cartId": 1,
                            "productId": 1,
                            "quantity": 1
                        }
                        """)
                .post("/api/v1/carts/addProduct")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldUpdateCart() {
        given()
                .contentType("application/json")
                .body("""
                        {
                            "name": "string",
                            "email": "string"
                        }
                        """)
                .post("/api/v1/customers");

        given()
                .contentType("application/json")
                .body("""
                        {
                         "name": "Elma",
                         "description": "Amasya Elması",
                         "price": 10,
                         "stock": 20
                       }
                        """)
                .post("/api/v1/products")
                .then()
                .statusCode(201);

        given()
                .contentType("application/json")
                .body("""
                        {
                            "cartId": 1,
                            "productId": 1,
                            "quantity": 1
                        }
                        """)
                .post("/api/v1/carts/addProduct")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .body("""
                        [
                            {
                                "productId": 1,
                                "quantity": 1
                            }
                        ]
                        """)
                .put("/api/v1/carts/1")
                .then()
                .statusCode(200);
    }

}
