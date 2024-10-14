package com.enoca.ecommerce.controller;

import com.enoca.ecommerce.AbstractIntegrationTest;
import com.enoca.ecommerce.repository.OrderRepository;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class OrderControllerTests  extends AbstractIntegrationTest {


    @Test
    void shouldPlaceOrder() {
        // create a customer , product and add product to cart
        given()
                .contentType("application/json")
                .body("""
                        {
                            "name": "string",
                            "email": "string"
                        }
                        """)
                .log().all()
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
                .log().all()
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
                .log().all()
                .statusCode(200);

        given()
                .contentType("application/json")
                .post("/api/v1/orders/placeOrder/1")  // Include the cartId in the path
                .then()
                .log().all()
                .statusCode(201);

    }

    @Test
    void shouldGetOrders() {
        given()
                .contentType("application/json")
                .body("""
                        {
                            "name": "string",
                            "email": "string"
                        }
                        """)
                .log().all()
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
                .log().all()
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
                .log().all()
                .statusCode(200);

        given()
                .contentType("application/json")
                .post("/api/v1/orders/placeOrder/1")  // Include the cartId in the path
                .then()
                .log().all()
                .statusCode(201);
        given()
                .contentType("application/json")
                .get("/api/v1/orders")
                .then()
                .log().all()
                .statusCode(200);
    }

//    @Test
//    void shouldGetOrder() {
//        given()
//                .contentType("application/json")
//                .body("""
//                        {
//                            "name": "string",
//                            "email": "string"
//                        }
//                        """)
//                .log().all()
//                .post("/api/v1/customers");
//
//        given()
//                .contentType("application/json")
//                .body("""
//                        {
//                         "name": "Elma",
//                         "description": "Amasya Elması",
//                         "price": 10,
//                         "stock": 20
//                       }
//                        """)
//                .log().all()
//                .post("/api/v1/products")
//                .then()
//                .statusCode(201);
//
//        given()
//                .contentType("application/json")
//                .body("""
//                        {
//                            "cartId": 1,
//                            "productId": 1,
//                            "quantity": 1
//                        }
//                        """)
//                .post("/api/v1/carts/addProduct")
//                .then()
//                .log().all()
//                .statusCode(200);
//
//        ValidatableResponse validatableResponse = given()
//                .contentType("application/json")
//                .post("/api/v1/orders/placeOrder/1")  // Include the cartId in the path
//                .then()
//                .log().all()
//                .statusCode(201);
//
//        long id = validatableResponse.extract().body().jsonPath().getLong("id");
//        given()
//                .contentType("application/json")
//                .get("/api/v1/orders/"+id)
//                .then()
//                .log().all()
//                .statusCode(200);
//    }

    @Test
    void shouldGetOrderByOrderCode() {
        given()
                .contentType("application/json")
                .body("""
                        {
                            "name": "string",
                            "email": "string"
                        }
                        """)
                .log().all()
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
                .log().all()
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
                .log().all()
                .statusCode(200);

        ValidatableResponse validatableResponse = given()
                .contentType("application/json")
                .post("/api/v1/orders/placeOrder/1")  // Include the cartId in the path
                .then()
                .log().all()
                .statusCode(201);

        String orderCode = validatableResponse.extract().body().jsonPath().getString("orderCode");
        given()
                .contentType("application/json")
                .get("/api/v1/orders/orderCode/" + orderCode)
                .then()
                .log().all()
                .statusCode(200);
    }




}
