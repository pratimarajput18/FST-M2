package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3 {

    String reqBody1, reqBody2;
    RequestSpecification reqSpec;
    ResponseSpecification resSpec;

    @BeforeClass
    public void setup() {
        // Create response specification
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .addHeader("Content-Type", "application/json")
                .build();
        resSpec = new ResponseSpecBuilder()
                // Check status code in response
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status", equalTo("alive"))
                .build();
        reqBody1 = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";
        reqBody2 = "{\"id\": 77233, \"name\": \"Hansel\", \"status\": \"alive\"}";
    }

    @Test(priority = 1)
    public void postRequest() {

        Response response = given().spec(reqSpec)
                .body(reqBody1)
                .when().post();
        response.then().spec(resSpec);
        System.out.println(response.body().asPrettyString());

        response = given().spec(reqSpec)
                .body(reqBody2)
                .when().post();
        response.then().spec(resSpec);
        System.out.println(response.body().asPrettyString());
    }

    @DataProvider
    public Object[][] requestPetData() {
        Object[][] testData = new Object[][]{
                {77232, "Riley", "alive"},
                {77233, "Hansel", "alive"}
        };
        return testData;
    }

    @Test(dataProvider = "requestPetData", priority = 2)
    public void getRequest(int id, String name, String status) {
        Response response = given().spec(reqSpec)
                .pathParams("petId", id)
                .when().get("/{petId}");
        System.out.println(response.body().asPrettyString());
        response.then().spec(resSpec).body("name", equalTo(name));
    }

    @Test(dataProvider = "requestPetData", priority = 3)
    public void deletePetRequest(int id, String name, String status) {
        Response response = given().spec(reqSpec)
                .pathParams("petId", id)
                .when().delete("/{petId}");
        System.out.println(response.body().asPrettyString());
        response.then().statusCode(200)
                .body("message", equalTo(Integer.toString(id)));
    }
}
