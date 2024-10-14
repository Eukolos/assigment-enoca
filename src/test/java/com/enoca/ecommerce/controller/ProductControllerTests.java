package com.enoca.ecommerce.controller;

import com.enoca.ecommerce.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class ProductControllerTests extends AbstractIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldGetProductById() {
        given()
                .when().get("api/v1/products/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }


    @Test
    void shouldCreateProduct() {
        given()
                .contentType("application/json")
                .body("""
                                {
                                    "name":"test product",
                                    "price":100.0,
                                    "stock":10,
                                    "description":"test product description"
                                }
                        """)
                .when().post("api/v1/products")
                .then()
                .statusCode(201)
                .body("name", equalTo("test product"))
                .body("price", equalTo(100.0F));
    }

    @Test
    void shouldUpdateProduct() {
        given()
                .contentType("application/json")
                .body("""
                                {
                                    "id":1,
                                    "name":"updated product",
                                    "price":150.0,
                                    "stock":10,
                                    "description":"updated product description"
                                }
                        """)
                .when().put("api/v1/products")
                .then()
                .statusCode(200)
                .body("name", equalTo("updated product"))
                .body("price", equalTo(150.0F));
    }


//    @Test
//    void shouldDeleteProduct() {
//        given()
//                .when().delete("api/v1/products/1")
//                .then()
//                .statusCode(204);
//    }
}
