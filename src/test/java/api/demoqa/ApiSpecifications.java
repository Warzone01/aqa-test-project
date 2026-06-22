package api.demoqa;

import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

public final class ApiSpecifications {

    private static final ResponseSpecification JSON_RESPONSE = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .build();

    static {
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private ApiSpecifications() {
    }

    public static RequestSpecification request() {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(ApiConfig.baseUrl())
                .setContentType(ContentType.JSON)
                .addFilter(new DemoQaRetryFilter());

        if (Allure.getLifecycle().getCurrentTestCase().isPresent()) {
            builder.addFilter(new AllureRestAssured());
        }

        return builder.build();
    }

    public static RequestSpecification authorizedRequest(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(request())
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    public static ResponseSpecification jsonResponse() {
        return JSON_RESPONSE;
    }
}
