package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TextBoxPage {

    private final SelenideElement fullNameInput = $("#userName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement currentAddressInput = $("#currentAddress");
    private final SelenideElement permanentAddressInput = $("#permanentAddress");
    private final SelenideElement outputBlock = $("#output");
    private final SelenideElement submitButton = $("#submit");
    private final SelenideElement nameOutput = $("#name");
    private final SelenideElement emailOutput = $("#email");
    private final SelenideElement currentAddressOutput = $("#output #currentAddress");
    private final SelenideElement permanentAddressOutput = $("#output #permanentAddress");

    @Step("Открыть страницу с текстовыми полями")
    public TextBoxPage openPage() {
        open("/text-box");
        submitButton.shouldBe(visible);
        return this;
    }

    @Step("Ввести полное имя: {name}")
    public TextBoxPage setFullName(String name) {
        fullNameInput.setValue(name);
        return this;
    }

    @Step("Ввести текущий адрес: {address}")
    public TextBoxPage setCurrentAddress(String address) {
        currentAddressInput.setValue(address);
        return this;
    }

    @Step("Ввести постоянный адрес: {address}")
    public TextBoxPage setPermanentAddress(String address) {
        permanentAddressInput.setValue(address);
        return this;
    }

    @Step("Ввести email: {email}")
    public TextBoxPage setEmail(String email) {
        emailInput.setValue(email);
        return this;
    }

    @Step("Отправить форму")
    public TextBoxPage submit() {
        submitButton.click();
        return this;
    }

    @Step("Проверить ошибку в поле email")
    public TextBoxPage emailShouldHaveError() {
        emailInput.shouldHave(cssClass("field-error"));
        return this;
    }

    @Step("Проверить отсутствие ошибки в поле email")
    public TextBoxPage emailShouldNotHaveError() {
        emailInput.shouldNotHave(cssClass("field-error"));
        return this;
    }

    @Step("Проверить выведенное имя: {name}")
    public TextBoxPage nameOutputShouldBe(String name) {
        outputBlock.shouldBe(visible);
        nameOutput.shouldHave(exactText("Name:" + name));
        return this;
    }

    @Step("Проверить выведенный email: {email}")
    public TextBoxPage emailOutputShouldBe(String email) {
        outputBlock.shouldBe(visible);
        emailOutput.shouldHave(exactText("Email:" + email));
        return this;
    }

    @Step("Проверить выведенный текущий адрес: {address}")
    public TextBoxPage currentAddressOutputShouldBe(String address) {
        outputBlock.shouldBe(visible);
        currentAddressOutput.shouldHave(exactText("Current Address :" + address));
        return this;
    }

    @Step("Проверить выведенный постоянный адрес: {address}")
    public TextBoxPage permanentAddressOutputShouldBe(String address) {
        outputBlock.shouldBe(visible);
        permanentAddressOutput.shouldHave(exactText("Permananet Address :" + address));
        return this;
    }
}
