package tests;

import data.TextBoxData;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.TextBoxPage;

@Feature("Текстовые поля")
@DisplayName("Проверка текстовых полей")
@Tag("regression")
public class TextBoxTest extends BaseTest {

    private final TextBoxPage textBoxPage = new TextBoxPage();

    @Story("Полное имя")
    @DisplayName("Введённое полное имя отображается после отправки формы")
    @Test
    @Tag("smoke")
    void fullNameTest() {
        textBoxPage
                .openPage()
                .setFullName(TextBoxData.FULL_NAME)
                .submit()
                .nameOutputShouldBe(TextBoxData.FULL_NAME);
    }

    @Story("Невалидный email")
    @DisplayName("Невалидный email подсвечивается ошибкой")
    @ParameterizedTest(name = "Невалидный email: {0}")
    @ValueSource(strings = {TextBoxData.INVALID_EMAIL, "test@", "@test.com"})
    @Tag("negative")
    void invalidEmailTest(String email) {
        textBoxPage
                .openPage()
                .setEmail(email)
                .submit()
                .emailShouldHaveError();
    }

    @Story("Валидный email")
    @DisplayName("Валидный email отображается после отправки формы")
    @Test
    void validEmailTest() {
        textBoxPage
                .openPage()
                .setEmail(TextBoxData.VALID_EMAIL)
                .submit()
                .emailShouldNotHaveError()
                .emailOutputShouldBe(TextBoxData.VALID_EMAIL);
    }

    @Story("Текущий адрес")
    @DisplayName("Введённый текущий адрес отображается после отправки формы")
    @Test
    void currentAddressTest() {
        textBoxPage
                .openPage()
                .setCurrentAddress(TextBoxData.CURRENT_ADDRESS)
                .submit()
                .currentAddressOutputShouldBe(TextBoxData.CURRENT_ADDRESS);
    }

    @Story("Постоянный адрес")
    @DisplayName("Введённый постоянный адрес отображается после отправки формы")
    @Test
    void permanentAddressTest() {
        textBoxPage
                .openPage()
                .setPermanentAddress(TextBoxData.PERMANENT_ADDRESS)
                .submit()
                .permanentAddressOutputShouldBe(TextBoxData.PERMANENT_ADDRESS);
    }
}
