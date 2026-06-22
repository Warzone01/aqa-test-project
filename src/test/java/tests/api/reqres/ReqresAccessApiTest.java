package tests.api.reqres;

import api.reqres.ReqresClient;
import api.reqres.model.AccessRequestResult;
import api.reqres.model.AppUserCounter;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Reqres API: доступ операторов")
@DisplayName("API доступа операторов Reqres")
@Tag("api")
@Tag("reqres")
@Tag("regression")
public class ReqresAccessApiTest {

    private final ReqresClient reqresClient = new ReqresClient();

    @Story("Конфигурация проекта")
    @DisplayName("Проект Reqres возвращает количество пользователей с доступом")
    @Test
    @Tag("smoke")
    void projectUsersTotalIsAvailable() {
        AppUserCounter counter = reqresClient.getProjectUsersTotalSuccessfully();

        assertAll(
                () -> assertNotNull(counter.total(), "Количество пользователей проекта должно быть заполнено"),
                () -> assertTrue(counter.total() >= 0, "Количество пользователей проекта не может быть отрицательным")
        );
    }

    @Story("Запрос доступа")
    @DisplayName("Валидный email принимает запрос на код доступа")
    @Test
    @Tag("smoke")
    void validEmailCanRequestAccessCode() {
        String email = "aqa." + UUID.randomUUID() + "@example.com";

        AccessRequestResult result = reqresClient.requestAccessSuccessfully(email);

        assertAll(
                () -> assertNotNull(result.data(), "Ответ должен содержать данные запроса доступа"),
                () -> assertTrue(result.data().sent(), "Код доступа должен быть отправлен"),
                () -> assertNotNull(result.data().expiresInMinutes(), "В ответе должен быть срок действия кода"),
                () -> assertTrue(result.data().expiresInMinutes() > 0, "Срок действия кода должен быть положительным")
        );
    }

    @Story("Валидация email")
    @DisplayName("Некорректный email отклоняется при запросе доступа")
    @Test
    @Tag("negative")
    void invalidEmailCannotRequestAccessCode() {
        reqresClient.requestAccess("not-an-email")
                .then()
                .statusCode(400);
    }

    @Story("Подтверждение доступа")
    @DisplayName("Неверный код доступа не создаёт сессию")
    @Test
    @Tag("negative")
    void invalidAccessCodeCannotBeVerified() {
        reqresClient.verifyAccessCode("invalid-code")
                .then()
                .statusCode(400);
    }
}
