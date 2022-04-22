package com.dxogo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

class RestAssuredTest {

    String api_url = "https://jsonplaceholder.typicode.com/todos";

    @Test
    void testApiEndpoint() { given().when().get(api_url).then().statusCode(200); }

    @Test
    void queryToDo4() {
        given().when().get(api_url + "/4")
                .then().statusCode(200)
                .and().body("title", equalTo("et porro tempora"))
                .and().body("id", equalTo(4));
    }

    @Test
    void testListAllToDosInclude198And199() {
        given().when().get(api_url)
                .then().statusCode(200)
                .and().body("id", hasItems(198, 199));
    }
}
