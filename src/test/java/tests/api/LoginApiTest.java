package tests.api;

import api.model.Token;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("DemoQA API: авторизация")
@DisplayName("API авторизации")
@Tag("api")
@Tag("regression")
public class LoginApiTest extends BaseApiTest {

    @Story("Успешная авторизация")
    @DisplayName("Зарегистрированный пользователь может войти и получает активный токен")
    @Test
    @Tag("smoke")
    void registeredUserCanLogIn() {
        assertTrue(accountClient.authorize(credentials).as(Boolean.class));
    }

    @Story("Генерация токена")
    @DisplayName("Сгенерированный токен авторизует зарегистрированного пользователя")
    @Test
    void generatedTokenAuthorizesUser() {
        Token generatedToken = accountClient.generateTokenSuccessfully(credentials);
        token = generatedToken.token();

        assertAll(
                () -> assertEquals("Success", generatedToken.status()),
                () -> assertNotNull(generatedToken.expires()),
                () -> assertTrue(accountClient.authorize(credentials).as(Boolean.class))
        );
    }

    @Story("Невалидные учётные данные")
    @DisplayName("Вход с неверным паролем отклоняется")
    @Test
    @Tag("negative")
    void invalidPasswordIsRejected() {
        Token rejectedToken = accountClient.generateToken(
                        new api.model.Credentials(credentials.userName(), "Wrong!Pass9")
                )
                .then()
                .statusCode(200)
                .extract()
                .as(Token.class);

        assertAll(
                () -> assertNull(rejectedToken.token()),
                () -> assertEquals("Failed", rejectedToken.status()),
                () -> assertEquals("User authorization failed.", rejectedToken.result())
        );
    }
}
