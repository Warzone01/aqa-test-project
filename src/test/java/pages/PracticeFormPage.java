package pages;

import com.codeborne.selenide.SelenideElement;
import data.PracticeFormData;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.ENTER;

public class PracticeFormPage {

    private final SelenideElement form = $("#userForm");
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement mobileInput = $("#userNumber");
    private final SelenideElement dateOfBirthInput = $("#dateOfBirthInput");
    private final SelenideElement subjectsInput = $("#subjectsInput");
    private final SelenideElement pictureInput = $("#uploadPicture");
    private final SelenideElement addressInput = $("#currentAddress");
    private final SelenideElement stateInput = $("#react-select-3-input");
    private final SelenideElement cityInput = $("#react-select-4-input");
    private final SelenideElement submitButton = $("#submit");
    private final SelenideElement resultModal = $(".modal-content");

    @Step("Открыть страницу с формой регистрации студента")
    public PracticeFormPage openPage() {
        open("/automation-practice-form");
        form.shouldBe(visible);
        return this;
    }

    @Step("Заполнить форму регистрации студента")
    public PracticeFormPage fillForm(PracticeFormData student) {
        fillRequiredFields(student.firstName(), student.lastName(), student.gender(), student.mobile());
        emailInput.setValue(student.email());
        selectDateOfBirth(student.birthDay(), student.birthMonth(), student.birthYear());
        student.subjects().forEach(this::selectSubject);
        student.hobbies().forEach(this::selectHobby);
        pictureInput.uploadFromClasspath(student.picture());
        addressInput.setValue(student.address());
        selectState(student.state());
        selectCity(student.city());
        return this;
    }

    @Step("Заполнить обязательные поля формы")
    public PracticeFormPage fillRequiredFields(
            String firstName,
            String lastName,
            String gender,
            String mobile
    ) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        selectGender(gender);
        mobileInput.setValue(mobile);
        return this;
    }

    @Step("Ввести email: {email}")
    public PracticeFormPage setEmail(String email) {
        emailInput.setValue(email);
        return this;
    }

    @Step("Ввести номер телефона: {mobile}")
    public PracticeFormPage setMobile(String mobile) {
        mobileInput.setValue(mobile);
        return this;
    }

    @Step("Выбрать штат: {state}")
    public PracticeFormPage selectState(String state) {
        $("#state").click();
        stateInput.setValue(state).press(ENTER);
        return this;
    }

    @Step("Выбрать город: {city}")
    public PracticeFormPage selectCity(String city) {
        $("#city").click();
        cityInput.setValue(city).press(ENTER);
        return this;
    }

    @Step("Отправить форму регистрации студента")
    public PracticeFormPage submit() {
        executeJavaScript("arguments[0].click()", submitButton);
        return this;
    }

    @Step("Проверить данные студента в итоговой таблице")
    public PracticeFormPage resultShouldContain(PracticeFormData student) {
        resultModal.shouldBe(visible);
        resultRowShouldBe("Student Name", student.fullName());
        resultRowShouldBe("Student Email", student.email());
        resultRowShouldBe("Gender", student.gender());
        resultRowShouldBe("Mobile", student.mobile());
        resultRowShouldBe("Date of Birth", student.dateOfBirth());
        resultRowShouldBe("Subjects", String.join(", ", student.subjects()));
        resultRowShouldBe("Hobbies", String.join(", ", student.hobbies()));
        resultRowShouldBe("Picture", student.picture());
        resultRowShouldBe("Address", student.address());
        resultRowShouldBe("State and City", student.state() + " " + student.city());
        return this;
    }

    @Step("Проверить валидацию обязательных полей")
    public PracticeFormPage requiredFieldsShouldBeInvalid() {
        form.shouldHave(cssClass("was-validated"));
        $$("#userForm input:invalid").shouldHave(size(6));
        resultModal.shouldBe(hidden);
        return this;
    }

    @Step("Проверить количество полей с ошибкой: {expectedCount}")
    public PracticeFormPage invalidFieldCountShouldBe(int expectedCount) {
        form.shouldHave(cssClass("was-validated"));
        $$("#userForm input:invalid").shouldHave(size(expectedCount));
        resultModal.shouldBe(hidden);
        return this;
    }

    @Step("Проверить, что выбор города недоступен")
    public PracticeFormPage cityShouldBeDisabled() {
        cityInput.shouldBe(disabled);
        return this;
    }

    @Step("Проверить, что выбор города доступен")
    public PracticeFormPage cityShouldBeEnabled() {
        cityInput.shouldBe(enabled);
        return this;
    }

    @Step("Проверить выбранный штат и город")
    public PracticeFormPage stateAndCityShouldBe(String state, String city) {
        $("#state").shouldHave(text(state));
        $("#city").shouldHave(text(city));
        return this;
    }

    private void selectGender(String gender) {
        $x("//label[normalize-space()='" + gender + "' and starts-with(@for, 'gender-radio-')]").click();
    }

    private void selectHobby(String hobby) {
        $x("//label[normalize-space()='" + hobby + "' and starts-with(@for, 'hobbies-checkbox-')]").click();
    }

    private void selectSubject(String subject) {
        subjectsInput.setValue(subject).press(ENTER);
    }

    private void selectDateOfBirth(String day, String month, String year) {
        dateOfBirthInput.click();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(year);

        // Исключаем дни соседних месяцев, которые также отображаются в календаре.
        $$(".react-datepicker__day:not(.react-datepicker__day--outside-month)")
                .findBy(exactText(day))
                .click();
    }

    private void resultRowShouldBe(String label, String expectedValue) {
        SelenideElement row = $$(".table-responsive tbody tr").findBy(text(label)).shouldBe(visible);
        row.$$("td").get(0).shouldHave(exactText(label));
        row.$$("td").get(1).shouldHave(exactText(expectedValue));
    }
}
