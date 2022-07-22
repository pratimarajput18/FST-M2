package gitRestProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitRestTest {

    RequestSpecification reqSpec;
    String token= "token";
    int id;

    @BeforeClass
    public void setup(){
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", token)
                .build();

    }

    @Test(priority = 1)
    public void postMethod(){
        String requestBody = "{\"title\": \"TestAPIKey\",\"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC7nVpeUgtrbkI2KLK8bPD5fD6+HDUkP8JaZ3JLioCKJ28ThHOFm0gdbY1VcAJ8jnfTidCEj5M7MGo2lfikWT59UP/+byXZU5gd80PBD9fGOEbl8/qtZEs9hRNPWCcrhKZUbp13+F26MFAxxekw39cEevUCR2lz9MbZ9ME+Y/SE4E3wssVHl9Sdn1ASwKQ/wca217FaY0Z6N4CszemFvIWogSRkdLcRAZHaeGtbW9Lb0Y4ZLgavI2Jq09du0XvV9Dxjaas3w1CsLHJHXf4vRSGKSR+kUhH9cKjDBlXisTkA3AhuepsjHaGwxT6bNWGmHQbrZqNhqjaXm13gbEI2/3UH \"}";
        Response response = given().spec(reqSpec)
                .body(requestBody)
                .when().post("/user/keys");
        id = response.then().extract().path("id");
        System.out.println("Generated Id : "+ id);
        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getMethod(){
        Response response = given().spec(reqSpec)
                .pathParams("keyId", id)
                .when().get("/user/keys/{keyId}");
        System.out.println(response.body().asPrettyString());
        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteMethod(){
        Response response = given().spec(reqSpec)
                .pathParams("keyId", id)
                .when().delete("/user/keys/{keyId}");
        System.out.println(response.body().asPrettyString());
        response.then().statusCode(204);
    }
}
