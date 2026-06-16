package api.reqres;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

public final class ReqresSpecifications {

    private static final String API_KEY_HEADER = "x-api-key";

    private static final ResponseSpecification JSON_RESPONSE = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .build();

    static {
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private ReqresSpecifications() {
    }

    public static RequestSpecification publicRequest() {
        return baseRequest()
                .addHeader(API_KEY_HEADER, ReqresConfig.publicApiKey())
                .build();
    }

    public static RequestSpecification manageRequest() {
        return baseRequest()
                .addHeader(API_KEY_HEADER, ReqresConfig.manageApiKey())
                .build();
    }

    public static RequestSpecification sessionRequest(String token) {
        return baseRequest()
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    public static RequestSpecification requestWithoutAuthorization() {
        return baseRequest().build();
    }

    public static ResponseSpecification jsonResponse() {
        return JSON_RESPONSE;
    }

    private static RequestSpecBuilder baseRequest() {
        return new RequestSpecBuilder()
                .setBaseUri(ReqresConfig.baseUrl())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured());
    }
}
