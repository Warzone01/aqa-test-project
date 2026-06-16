package api;

import api.model.Credentials;
import api.model.Token;
import api.model.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.ApiSpecifications.authorizedRequest;
import static api.ApiSpecifications.jsonResponse;
import static api.ApiSpecifications.request;
import static io.restassured.RestAssured.given;

public class AccountClient {

    @Step("Создать пользователя через API")
    public Response createUser(Credentials credentials) {
        return given()
                .spec(request())
                .body(credentials)
                .when()
                .post("/Account/v1/User");
    }

    @Step("Сгенерировать токен авторизации")
    public Response generateToken(Credentials credentials) {
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

}
