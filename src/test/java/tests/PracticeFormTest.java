package tests;

import data.PracticeFormData;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.PracticeFormPage;

@Feature("Форма регистрации студента")
@DisplayName("Проверка формы регистрации студента")
@Tag("regression")
public class PracticeFormTest extends BaseTest {

    private final PracticeFormPage practiceFormPage = new PracticeFormPage();

    @Story("Успешная регистрация")
    @DisplayName("Полностью заполненная форма отправляется, а введённые данные отображаются в результате")
    @Test
    @Tag("smoke")
    void submitCompletedForm() {
        PracticeFormData student = PracticeFormData.validStudent();

        practiceFormPage
                .openPage()
                .fillForm(student)
                .submit()
                .resultShouldContain(student);
    }

    @Story("Валидация обязательных полей")
    @DisplayName("Пустую форму нельзя отправить")
    @Test
    @Tag("negative")
    void validateRequiredFields() {
        practiceFormPage
                .openPage()
                .submit()
                .requiredFieldsShouldBeInvalid();
    }

    @Story("Валидация форматов полей")
    @DisplayName("Email и телефон проверяются на соответствие формату")
    @Test
    @Tag("negative")
    void validateEmailAndMobileFormats() {
        PracticeFormData student = PracticeFormData.validStudent();

        practiceFormPage
                .openPage()
                .fillRequiredFields(
                        student.firstName(),
                        student.lastName(),
                        student.gender(),
                        student.mobile()
                )
                .setEmail("invalid-email")
                .setMobile("12345")
                .submit()
                .invalidFieldCountShouldBe(2);
    }

    @Story("Зависимые списки")
    @DisplayName("Город становится доступен только после выбора штата")
    @Test
    void selectStateAndCity() {
        PracticeFormData student = PracticeFormData.validStudent();

        practiceFormPage
                .openPage()
                .cityShouldBeDisabled()
                .selectState(student.state())
                .cityShouldBeEnabled()
                .selectCity(student.city())
                .stateAndCityShouldBe(student.state(), student.city());
    }
}
