package tests.api.reqres;

import api.reqres.ReqresClient;
import api.reqres.model.TaskData;
import api.reqres.model.TaskRecordRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("Reqres API: задачи")
@DisplayName("API задач Reqres")
@Tag("api")
@Tag("reqres")
@Tag("regression")
public class ReqresTaskApiTest {

    private final ReqresClient reqresClient = new ReqresClient();

    @Story("Защита задач")
    @DisplayName("Список задач нельзя получить без сессионного токена")
    @Test
    @Tag("negative")
    void taskRecordsCannotBeReadWithoutSessionToken() {
        reqresClient.getTaskRecordsWithoutAuthorization()
                .then()
                .statusCode(401);
    }

    @Story("Защита задач")
    @DisplayName("Задачу нельзя создать с неверным сессионным токеном")
    @Test
    @Tag("negative")
    void taskRecordCannotBeCreatedWithInvalidSessionToken() {
        TaskData taskData = new TaskData(
                "Подготовить тестовый отчёт",
                "Проверка защиты API Reqres от запросов с неверной сессией",
                false
        );

        reqresClient.createTaskRecord("invalid-session-token", new TaskRecordRequest(taskData))
                .then()
                .statusCode(401);
    }
}
