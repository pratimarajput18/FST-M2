package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    Map<String, String> requestHeaders = new HashMap<>();
    String resource_path = "/api/users";


    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        //Set Headers
        requestHeaders.put("Content-Type", "application/json");

        //create body
        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id", 123)
                .stringType("firstName", "Pratima")
                .stringType("lastName", "Rajput")
                .stringType("email", "pratima@gmail.com");

        //Contract creation
        return builder.given("Request to create a user ")
                .uponReceiving("Request to create a user ")
                .method("POST")
                .headers(requestHeaders)
                .path(resource_path)
                .body(reqResBody)
                .willRespondWith()
                .status(201)
                .body(reqResBody)
                .toPact();
    }
    //        Note : TestNG wont support with Pact. Pact supports JUnit with Jupieter

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")   // creates Mock Server
    public void consumerTest() {
        // Set the baseURI
        String baseURI = "http://localhost:8282";

        // Set the request body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 123);
        reqBody.put("firstName", "Pratima");
        reqBody.put("lastName", "Rajput");
        reqBody.put("email", "pratimarajput@gmail.com");

        // Generate Response
        Response response = given().headers(requestHeaders).body(reqBody)
                .when().post(baseURI + resource_path);

        System.out.println(response.body().asPrettyString());

        response.then().statusCode(201);
    }

}
