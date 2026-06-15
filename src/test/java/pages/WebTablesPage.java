package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebTablesPage {

    // tablets page
    private final SelenideElement addButton = $("#addNewRecordButton");

    // registration Form
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement ageInput = $("#age");
    private final SelenideElement salaryInput = $("#salary");
    private final SelenideElement departmentInput = $("#department");
    private final SelenideElement submitButton = $("#submit");

    public WebTablesPage openPage() {
        open("/webtables");
        addButton.shouldBe(visible);
        return this;
    }

    public WebTablesPage clickAddButton() {
        addButton.click();
        return this;
    }

    public WebTablesPage addNewRecord() {
        addButton.click();
        firstNameInput.shouldBe(visible);
        firstNameInput.setValue("KIrill");
        lastNameInput.setValue("Gulvaniuk");
        emailInput.setValue("kirill@email.com");
        ageInput.setValue("26");
        salaryInput.setValue("123");
        departmentInput.setValue("NYC");
        submitButton.click();
        addButton.shouldBe(visible);
        return this;
    }
}
