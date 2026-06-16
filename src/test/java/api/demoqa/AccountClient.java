package api.demoqa;

import api.demoqa.model.Credentials;
import api.demoqa.model.Token;
import api.demoqa.model.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.demoqa.ApiSpecifications.authorizedRequest;
import static api.demoqa.ApiSpecifications.jsonResponse;
import static api.demoqa.ApiSpecifications.request;
import static io.restassured.RestAssured.given;

public class AccountClient {

    @Step("Создать пользователя через API")
    public Response createUser(Credentials credentials) {
        return sendCreateUserRequest(credentials);
    }

    public Response createUserForSetup(Credentials credentials) {
        return sendCreateUserRequest(credentials);
    }

    private Response sendCreateUserRequest(Credentials credentials) {
        return given()
                .spec(request())
                .body(credentials)
                .when()
                .post("/Account/v1/User");
    }

    @Step("Сгенерировать токен авторизации")
    public Response generateToken(Credentials credentials) {
        return sendGenerateTokenRequest(credentials);
    }

    public Response generateTokenForSetup(Credentials credentials) {
        return sendGenerateTokenRequest(credentials);
    }

    private Response sendGenerateTokenRequest(Credentials credentials) {
        return given()
                .spec(request())
                .body(credentials)
                .when()
                .post("/Account/v1/GenerateToken");
    }

    @Step("Проверить, авторизован ли пользователь")
    public Response authorize(Credentials credentials) {
        return given()
                .spec(request())
                .body(credentials)
                .when()
                .post("/Account/v1/Authorized");
    }

    @Step("Получить профиль пользователя")
    public Response getUser(String userId, String token) {
        return given()
                .spec(authorizedRequest(token))
                .when()
                .get("/Account/v1/User/{userId}", userId);
    }

    @Step("Удалить пользователя через API")
    public Response deleteUser(String userId, String token) {
        return sendDeleteUserRequest(userId, token);
    }

    public Response deleteUserForSetup(String userId, String token) {
        return sendDeleteUserRequest(userId, token);
    }

    private Response sendDeleteUserRequest(String userId, String token) {
        return given()
                .spec(authorizedRequest(token))
                .when()
                .delete("/Account/v1/User/{userId}", userId);
    }

    public User createUserSuccessfully(Credentials credentials) {
        return createUser(credentials)
                .then()
                .spec(jsonResponse())
                .statusCode(201)
                .extract()
                .as(User.class);
    }

    public Token generateTokenSuccessfully(Credentials credentials) {
        return generateToken(credentials)
                .then()
                .spec(jsonResponse())
                .statusCode(200)
                .extract()
                .as(Token.class);
    }

    public User createUserForSetupSuccessfully(Credentials credentials) {
        return createUserForSetup(credentials)
                .then()
                .spec(jsonResponse())
                .statusCode(201)
                .extract()
                .as(User.class);
    }

    public Token generateTokenForSetupSuccessfully(Credentials credentials) {
        return generateTokenForSetup(credentials)
                .then()
                .spec(jsonResponse())
                .statusCode(200)
                .extract()
                .as(Token.class);
    }

}
