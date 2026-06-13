package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ButtonsPage;

@Feature("Кнопки")
@DisplayName("Проверка действий с кнопками")
public class ButtonsTest extends BaseTest {

    private final ButtonsPage buttonsPage = new ButtonsPage();

    @Story("Двойное нажатие")
    @DisplayName("Двойное нажатие отображает соответствующее сообщение")
    @Test
    void checkDoubleClick() {
        buttonsPage
                .openPage()
                .doubleClickMessageHidden()
                .doubleClickButton()
                .doubleClickMessageVisible()
                .defaultClickMessageHidden()
                .rightClickMessageHidden();
    }

    @Story("Нажатие правой кнопкой мыши")
    @DisplayName("Нажатие правой кнопкой мыши отображает соответствующее сообщение")
    @Test
    void checkRightClick() {
        buttonsPage
                .openPage()
                .rightClickMessageHidden()
                .rightClickButton()
                .rightClickMessageVisible()
                .defaultClickMessageHidden()
                .doubleClickMessageHidden();
    }

    @Story("Обычное нажатие")
    @DisplayName("Обычное нажатие отображает соответствующее сообщение")
    @Test
    void checkDefaultClick() {
        buttonsPage
                .openPage()
                .defaultClickMessageHidden()
                .defaultClickButton()
                .defaultClickMessageVisible()
                .doubleClickMessageHidden()
                .rightClickMessageHidden();
    }
}
