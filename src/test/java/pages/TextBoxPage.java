package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class TextBoxPage extends BasicPage {

    private final SelenideElement fullNameInput = $("#userName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement currentAddressInput = $("#currentAddress");
    private final SelenideElement permanentAddressInput = $("#permanentAddress");

    private final SelenideElement outputBlock = $("#output");

    private final SelenideElement submitButton = $("#submit");

    private final String name = "Kirill";
    private final String currentAddress = "Vyborg";
    private final String permanentAddress = "SPB";

    @Step("Открыть страницу с инпутами")
    public TextBoxPage openPage() {
        open("/text-box");
        waitPageLoaded();
        return this;
    }

    @Step("Ввод текста в поле full name")
    public TextBoxPage fullNameInputText() {
        fullNameInput.setValue(name);
        return this;
    }

    @Step("Ввод текста в поле current address")
    public TextBoxPage currentAddressInputText() {
        currentAddressInput.setValue(currentAddress);
        return this;
    }

    @Step("Ввод текста в поле permanent address")
    public TextBoxPage permanentAddressInputText() {
        permanentAddressInput.setValue(permanentAddress);
        return this;
    }

    @Step("Ввод текста в поле email")
    public TextBoxPage emailInputText(String emailText) {
        emailInput.setValue(emailText);
        return this;
    }

    @Step("Нажатие на кнопку подтверждения")
    public TextBoxPage submitButtonClick() {
        submitButton.click();
        return this;
    }

    @Step("Проверить, что поле email содержит ошибку")
    public TextBoxPage emailShouldHaveError() {
        emailInput.shouldHave(cssClass("field-error"));
        return this;
    }

    @Step("Проверить, что поле email не содержит ошибку")
    public TextBoxPage emailShouldNotHaveError() {
        emailInput.shouldNotHave(cssClass("field-error"));
        return this;
    }

    @Step("Проверить, что поле name вывело результат")
    public TextBoxPage nameOutputCheck() {
        outputBlock.shouldHave(text(name));
        return this;
    }

    @Step("Проверить, что поле current Address вывело результат")
    public TextBoxPage currentAddressOutputCheck() {
        outputBlock.shouldHave(text(currentAddress));
        return this;
    }

    @Step("Проверить, что поле permanent address вывело результат")
    public TextBoxPage permanentAddressOutputCheck() {
        outputBlock.shouldHave(text(permanentAddress));
        return this;
    }
}
