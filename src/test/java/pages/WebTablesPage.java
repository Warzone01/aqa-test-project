package pages;

import com.codeborne.selenide.SelenideElement;
import data.WebTableUser;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.BACK_SPACE;
import static org.openqa.selenium.Keys.CONTROL;

public class WebTablesPage {

    private final SelenideElement addButton = $("#addNewRecordButton");
    private final SelenideElement searchInput = $("#searchBox");
    private final SelenideElement tableBody = $("table tbody");
    private final SelenideElement pageInfo = $(".pagination strong");
    private final SelenideElement pageSizeSelect = $(".pagination select.form-control");
    private final SelenideElement nextButton =
            $x("//div[contains(@class, 'btn-group')]/button[normalize-space()='Next']");
    private final SelenideElement previousButton =
            $x("//div[contains(@class, 'btn-group')]/button[normalize-space()='Previous']");

    private final SelenideElement userForm = $("#userForm");
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement ageInput = $("#age");
    private final SelenideElement salaryInput = $("#salary");
    private final SelenideElement departmentInput = $("#department");
    private final SelenideElement submitButton = $("#submit");

    @Step("Открыть страницу с веб-таблицей")
    public WebTablesPage openPage() {
        open("/webtables");
        addButton.shouldBe(visible);
        return this;
    }

    @Step("Открыть форму регистрации")
    public WebTablesPage openRegistrationForm() {
        addButton.click();
        userForm.shouldBe(visible);
        return this;
    }

    @Step("Отправить форму регистрации")
    public WebTablesPage submitForm() {
        submitButton.click();
        return this;
    }

    @Step("Заполнить поля значениями неверного формата")
    public WebTablesPage fillInvalidFormattedFields() {
        firstNameInput.setValue("Test");
        lastNameInput.setValue("User");
        emailInput.setValue("invalid-email");
        ageInput.setValue("age");
        salaryInput.setValue("salary");
        departmentInput.setValue("Testing");
        return this;
    }

    @Step("Добавить пользователя {user.email}")
    public WebTablesPage addUser(WebTableUser user) {
        openRegistrationForm();
        fillForm(user);
        submitForm();
        userForm.shouldBe(hidden);
        return this;
    }

    @Step("Отредактировать пользователя {currentUser.email}")
    public WebTablesPage editUser(WebTableUser currentUser, WebTableUser updatedUser) {
        rowByEmail(currentUser.email()).$("span[title='Edit']").click();
        userForm.shouldBe(visible);
        fillForm(updatedUser);
        submitForm();
        userForm.shouldBe(hidden);
        return this;
    }

    @Step("Удалить пользователя {user.email}")
    public WebTablesPage deleteUser(WebTableUser user) {
        rowByEmail(user.email()).$("span[title='Delete']").click();
        return this;
    }

    @Step("Выполнить поиск в таблице по значению: {query}")
    public WebTablesPage search(String query) {
        searchInput.setValue(query);
        return this;
    }

    @Step("Очистить поиск по таблице")
    public WebTablesPage clearSearch() {
        searchInput.sendKeys(CONTROL + "a");
        searchInput.sendKeys(BACK_SPACE);
        searchInput.shouldHave(exactValue(""));
        return this;
    }

    @Step("Установить размер страницы: {pageSize}")
    public WebTablesPage setPageSize(int pageSize) {
        pageSizeSelect.selectOptionByValue(String.valueOf(pageSize));
        return this;
    }

    @Step("Перейти на следующую страницу таблицы")
    public WebTablesPage goToNextPage() {
        clickPaginationButton(nextButton);
        return this;
    }

    @Step("Перейти на предыдущую страницу таблицы")
    public WebTablesPage goToPreviousPage() {
        clickPaginationButton(previousButton);
        return this;
    }

    @Step("Проверить отображение пользователя {user.email}")
    public WebTablesPage userShouldBeDisplayed(WebTableUser user) {
        rowByEmail(user.email())
                .shouldHave(text(user.firstName()))
                .shouldHave(text(user.lastName()))
                .shouldHave(text(String.valueOf(user.age())))
                .shouldHave(text(user.email()))
                .shouldHave(text(String.valueOf(user.salary())))
                .shouldHave(text(user.department()));
        return this;
    }

    @Step("Проверить отсутствие пользователя с email {email}")
    public WebTablesPage userShouldNotBeDisplayed(String email) {
        tableBody.shouldNotHave(text(email));
        return this;
    }

    @Step("Проверить количество отображаемых строк таблицы: {expectedCount}")
    public WebTablesPage rowCountShouldBe(int expectedCount) {
        $$("table tbody tr").shouldHave(size(expectedCount));
        return this;
    }

    @Step("Проверить текущую страницу: {expectedPageInfo}")
    public WebTablesPage pageInfoShouldBe(String expectedPageInfo) {
        pageInfo.shouldHave(exactText(expectedPageInfo));
        return this;
    }

    @Step("Проверить количество невалидных полей: {expectedCount}")
    public WebTablesPage invalidFieldCountShouldBe(int expectedCount) {
        userForm.shouldBe(visible).shouldHave(cssClass("was-validated"));
        $$("#userForm input:invalid").shouldHave(size(expectedCount));
        return this;
    }

    private void fillForm(WebTableUser user) {
        firstNameInput.setValue(user.firstName());
        lastNameInput.setValue(user.lastName());
        emailInput.setValue(user.email());
        ageInput.setValue(String.valueOf(user.age()));
        salaryInput.setValue(String.valueOf(user.salary()));
        departmentInput.setValue(user.department());
    }

    private SelenideElement rowByEmail(String email) {
        return $$("table tbody tr").findBy(text(email)).shouldBe(visible);
    }

    private void clickPaginationButton(SelenideElement button) {
        String currentPageInfo = pageInfo.shouldBe(visible).getText();
        button.shouldBe(visible, enabled);
        executeJavaScript("arguments[0].click()", button);
        pageInfo.shouldNotHave(exactText(currentPageInfo));
    }
}
