package api.reqres;

import api.reqres.model.AccessCodeVerification;
import api.reqres.model.AccessRequest;
import api.reqres.model.AccessRequestResult;
import api.reqres.model.AppUserCounter;
import api.reqres.model.TaskRecordRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.reqres.ReqresSpecifications.jsonResponse;
import static api.reqres.ReqresSpecifications.manageRequest;
import static api.reqres.ReqresSpecifications.publicRequest;
import static api.reqres.ReqresSpecifications.requestWithoutAuthorization;
import static api.reqres.ReqresSpecifications.sessionRequest;
import static io.restassured.RestAssured.given;

public class ReqresClient {

    @Step("Получить количество пользователей проекта Reqres")
    public Response getProjectUsersTotal() {
        return given()
                .spec(publicRequest())
                .when()
                .get("/api/projects/{projectId}/app-users/total", ReqresConfig.projectId());
    }

    @Step("Запросить код доступа Reqres для email: {email}")
    public Response requestAccess(String email) {
        return given()
                .spec(publicRequest())
                .body(new AccessRequest(email, ReqresConfig.projectId()))
                .when()
                .post("/api/app-users/login");
    }

    @Step("Подтвердить код доступа Reqres")
    public Response verifyAccessCode(String accessCode) {
        return given()
                .spec(manageRequest())
                .body(new AccessCodeVerification(accessCode, ReqresConfig.projectId()))
                .when()
                .post("/api/app-users/verify");
    }

    @Step("Получить профиль текущего пользователя Reqres")
    public Response getCurrentUser(String sessionToken) {
        return given()
                .spec(sessionRequest(sessionToken))
                .when()
                .get("/api/app-users/me");
    }

    @Step("Получить список задач Reqres без авторизации")
    public Response getTaskRecordsWithoutAuthorization() {
        return given()
                .spec(requestWithoutAuthorization())
                .when()
                .get("/app/collections/{collectionSlug}/records", ReqresConfig.collectionSlug());
    }

    @Step("Создать задачу Reqres")
    public Response createTaskRecord(String sessionToken, TaskRecordRequest request) {
        return given()
                .spec(sessionRequest(sessionToken))
                .body(request)
                .when()
                .post("/app/collections/{collectionSlug}/records", ReqresConfig.collectionSlug());
    }

    public AppUserCounter getProjectUsersTotalSuccessfully() {
        return getProjectUsersTotal()
                .then()
                .spec(jsonResponse())
                .statusCode(200)
                .extract()
                .as(AppUserCounter.class);
    }

    public AccessRequestResult requestAccessSuccessfully(String email) {
        return requestAccess(email)
                .then()
                .spec(jsonResponse())
                .statusCode(200)
                .extract()
                .as(AccessRequestResult.class);
    }
}
