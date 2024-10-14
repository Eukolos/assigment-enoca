package com.enoca.ecommerce.controller;


import com.enoca.ecommerce.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class CustomerControllerTests extends AbstractIntegrationTest {




    @Test
    void shouldGetAllCustomersWithCart() {
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
                            "name": "string2",
                            "email": "string2"
                        }
                        """)
                .post("/api/v1/customers")
                .then();
        given()
                .when().get("/api/v1/customers/cart")
                .then()
                .statusCode(200)
                .body("[0].id", equalTo(1));
    }

    @Test
    void shouldCreateCustomer() {
        given()
                .contentType("application/json")
                .body("""
                        {
                            "name": "string",
                            "email": "string"
                        }
                        """)
                .when().post("/api/v1/customers")
                .then()
                .statusCode(201)
                .body("name", equalTo("string"))
                .body("email", equalTo("string"));
    }
    @Test
    void shouldGetCustomerById() {
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
                            "name": "string2",
                            "email": "string2"
                        }
                        """)
                .post("/api/v1/customers")
                .then();
        given()
                .when().get("/api/v1/customers/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("string"))
                .body("email", equalTo("string"));
    }


    @Test
    void shouldUpdateCustomer() {
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
                            "name": "string2",
                            "email": "string2"
                        }
                        """)
                .post("/api/v1/customers")
                .then();
        String updatedCustomerJson = """
            {
                "id": 2,
                "name": "updated",
                "email": "updated"
            }
            """;

        given()
                .contentType("application/json")
                .body(updatedCustomerJson)
                .when()
                .put("/api/v1/customers")
                .then()
                .statusCode(200)  // Expect a 200 OK response
                .body("id", equalTo(2 ))
                .body("name", equalTo("updated"))
                .body("email", equalTo("updated"));
    }


    @Test
    void shouldGetCustomerCartById() {
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
                            "name": "string2",
                            "email": "string2"
                        }
                        """)
                .post("/api/v1/customers")
                .then();
        given()
                .when().get("/api/v1/customers/cart/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                ;
    }

    @Test
    void shouldGetAllCustomers() {
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
                            "name": "string2",
                            "email": "string2"
                        }
                        """)
                .post("/api/v1/customers")
                .then();
        given()
                .when().get("/api/v1/customers")
                .then()
                .statusCode(200)
                .body("[0].id", equalTo(1))
                .body("[0].name", equalTo("string"))
                .body("[0].email", equalTo("string"));
    }

    @Test
    void shouldDeleteCustomer() {
        given()
                .when().delete("/api/v1/customers/1")
                .then()
                .statusCode(204);
    }
}

