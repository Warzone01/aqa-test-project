package tests;

import data.TextBoxData;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.TextBoxPage;

@DisplayName("Проверка текстовых полей")
public class TextBoxTest extends BaseTest {

    private final TextBoxPage textBoxPage = new TextBoxPage();

    @Story("Полное имя")
    @DisplayName("Введенное полное имя отображается после отправки формы")
    @Test
    void fullNameTest() {
        textBoxPage
                .openPage()
                .fullNameInputText()
                .submitButtonClick()
                .nameOutputCheck();
    }

    @Story("Невалидный email")
    @DisplayName("Невалидный email подсвечивается ошибкой")
    @Test
    void invalidEmailTest() {
        textBoxPage
                .openPage()
                .emailInputText(TextBoxData.INVALID_EMAIL)
                .submitButtonClick()
                .emailShouldHaveError();
    }

    @Story("Валидный email")
    @DisplayName("Валидный email не подсвечивается ошибкой")
    @Test
    void validEmailTest() {
        textBoxPage
                .openPage()
                .emailInputText(TextBoxData.VALID_EMAIL)
                .submitButtonClick()
                .emailShouldNotHaveError();
    }

    @Story("Текущий адрес")
    @DisplayName("Введенный текущий адрес отображается после отправки формы")
    @Test
    void currentAddressTest() {
        textBoxPage
                .openPage()
                .currentAddressInputText()
                .submitButtonClick()
                .currentAddressOutputCheck();
    }

    @Story("Постоянный адрес")
    @DisplayName("Введенный постоянный адрес отображается после отправки формы")
    @Test
    void permanentAddressTest() {
        textBoxPage
                .openPage()
                .permanentAddressInputText()
                .submitButtonClick()
                .permanentAddressOutputCheck();
    }
}
