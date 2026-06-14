package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class ButtonsPage {

    private final SelenideElement doubleClickButton = $("#doubleClickBtn");
    private final SelenideElement rightClickButton = $("#rightClickBtn");
    private final SelenideElement defaultClickButton = $x("(//button[text()='Click Me'])[1]");
    private final SelenideElement doubleClickMessage = $("#doubleClickMessage");
    private final SelenideElement rightClickMessage = $("#rightClickMessage");
    private final SelenideElement defaultClickMessage = $("#dynamicClickMessage");

    @Step("Открыть страницу с кнопками")
    public ButtonsPage openPage() {
        open("/buttons");
        doubleClickButton.shouldBe(visible);
        return this;
    }

    @Step("Дважды нажать на кнопку")
    public ButtonsPage doubleClickButton() {
        doubleClickButton.shouldBe(visible, enabled).doubleClick();
        return this;
    }

    @Step("Нажать на кнопку правой кнопкой мыши")
    public ButtonsPage rightClickButton() {
        rightClickButton.shouldBe(visible, enabled).contextClick();
        return this;
    }

    @Step("Нажать на обычную кнопку")
    public ButtonsPage defaultClickButton() {
        defaultClickButton.shouldBe(visible, enabled).click();
        return this;
    }

    @Step("Проверить сообщение о двойном нажатии")
    public ButtonsPage doubleClickMessageVisible() {
        doubleClickMessage.shouldBe(visible).shouldHave(exactText("You have done a double click"));
        return this;
    }

    @Step("Проверить отсутствие сообщения о двойном нажатии")
    public ButtonsPage doubleClickMessageHidden() {
        doubleClickMessage.shouldBe(hidden);
        return this;
    }

    @Step("Проверить сообщение о нажатии правой кнопкой мыши")
    public ButtonsPage rightClickMessageVisible() {
        rightClickMessage.shouldBe(visible).shouldHave(exactText("You have done a right click"));
        return this;
    }

    @Step("Проверить отсутствие сообщения о нажатии правой кнопкой мыши")
    public ButtonsPage rightClickMessageHidden() {
        rightClickMessage.shouldBe(hidden);
        return this;
    }

    @Step("Проверить сообщение об обычном нажатии")
    public ButtonsPage defaultClickMessageVisible() {
        defaultClickMessage.shouldBe(visible).shouldHave(exactText("You have done a dynamic click"));
        return this;
    }

    @Step("Проверить отсутствие сообщения об обычном нажатии")
    public ButtonsPage defaultClickMessageHidden() {
        defaultClickMessage.shouldBe(hidden);
        return this;
    }
}
