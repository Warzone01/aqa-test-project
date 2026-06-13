package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ButtonsPage extends BasicPage {

    private final SelenideElement doubleClickButton = $x("//*[@id=\"doubleClickBtn\"]");
    private final SelenideElement rightClickButton = $x("//*[@id=\"rightClickBtn\"]");
    private final SelenideElement defaultClickButton = $x("(//button[text()='Click Me'])[1]");

    private final SelenideElement doubleClickMessage = $x("//*[@id=\"doubleClickMessage\"]");
    private final SelenideElement rightClickMessage = $x("//*[@id=\"rightClickMessage\"]");
    private final SelenideElement defaultClickMessage = $x("//*[@id=\"dynamicClickMessage\"]");

    @Step("Открыть страницу с кнопками")
    public ButtonsPage openPage() {
        open("/buttons");
        waitPageLoaded();
        return this;
    }

    @Step("Дважды нажать на кнопку")
    public ButtonsPage doubleClickButton() {
        doubleClickButton
                .shouldBe(visible)
                .shouldBe(enabled)
                .doubleClick();
        return this;
    }

    @Step("Нажать на кнопку правой кнопкой мыши")
    public ButtonsPage rightClickButton() {
        rightClickButton
                .shouldBe(visible)
                .shouldBe(enabled)
                .contextClick();
        return this;
    }

    @Step("Нажать на обычную кнопку")
    public ButtonsPage defaultClickButton() {
        defaultClickButton
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        return this;
    }

    @Step("Проверить отображение сообщения о двойном нажатии")
    public ButtonsPage doubleClickMessageVisible() {
        doubleClickMessage.shouldBe(visible);
        return this;
    }

    @Step("Проверить отсутствие сообщения о двойном нажатии")
    public ButtonsPage doubleClickMessageHidden() {
        doubleClickMessage.shouldBe(hidden);
        return this;
    }

    @Step("Проверить отображение сообщения о нажатии правой кнопкой мыши")
    public ButtonsPage rightClickMessageVisible() {
        rightClickMessage.shouldBe(visible);
        return this;
    }

    @Step("Проверить отсутствие сообщения о нажатии правой кнопкой мыши")
    public ButtonsPage rightClickMessageHidden() {
        rightClickMessage.shouldBe(hidden);
        return this;
    }

    @Step("Проверить отображение сообщения об обычном нажатии")
    public ButtonsPage defaultClickMessageVisible() {
        defaultClickMessage.shouldBe(visible);
        return this;
    }

    @Step("Проверить отсутствие сообщения об обычном нажатии")
    public ButtonsPage defaultClickMessageHidden() {
        defaultClickMessage.shouldBe(hidden);
        return this;
    }
}
