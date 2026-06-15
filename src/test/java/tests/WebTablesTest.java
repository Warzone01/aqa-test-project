package tests;

import data.WebTableUser;
import data.WebTableUserFactory;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.WebTablesPage;

@Feature("Веб-таблица")
@DisplayName("Проверка веб-таблицы")
@Tag("regression")
public class WebTablesTest extends BaseTest {

    private final WebTablesPage webTablesPage = new WebTablesPage();

    @Story("Добавление пользователя")
    @DisplayName("Пользователь добавляется со сгенерированными данными")
    @Test
    @Tag("smoke")
    void addUser() {
        WebTableUser user = WebTableUserFactory.randomUser();

        webTablesPage
                .openPage()
                .addUser(user)
                .userShouldBeDisplayed(user);
    }

    @Story("Валидация формы")
    @DisplayName("Обязательные поля не позволяют отправить пустую форму")
    @Test
    @Tag("negative")
    void validateRequiredFields() {
        webTablesPage
                .openPage()
                .openRegistrationForm()
                .submitForm()
                .invalidFieldCountShouldBe(6);
    }

    @Story("Валидация формы")
    @DisplayName("Поля email, возраста и зарплаты проверяются на соответствие формату")
    @Test
    @Tag("negative")
    void validateFieldFormats() {
        webTablesPage
                .openPage()
                .openRegistrationForm()
                .fillInvalidFormattedFields()
                .submitForm()
                .invalidFieldCountShouldBe(3);
    }

    @Story("Редактирование пользователя")
    @DisplayName("Данные пользователя можно отредактировать")
    @Test
    void editUser() {
        WebTableUser originalUser = WebTableUserFactory.randomUser();
        WebTableUser updatedUser = WebTableUserFactory.randomUser();

        webTablesPage
                .openPage()
                .addUser(originalUser)
                .editUser(originalUser, updatedUser)
                .userShouldNotBeDisplayed(originalUser.email())
                .userShouldBeDisplayed(updatedUser);
    }

    @Story("Удаление пользователя")
    @DisplayName("Пользователя можно удалить")
    @Test
    void deleteUser() {
        WebTableUser user = WebTableUserFactory.randomUser();

        webTablesPage
                .openPage()
                .addUser(user)
                .deleteUser(user)
                .userShouldNotBeDisplayed(user.email());
    }

    @Story("Поиск пользователей")
    @DisplayName("Поиск фильтрует строки и очищается")
    @Test
    void searchAndClear() {
        WebTableUser user = WebTableUserFactory.randomUser();

        webTablesPage
                .openPage()
                .addUser(user)
                .search(user.email())
                .rowCountShouldBe(1)
                .userShouldBeDisplayed(user)
                .clearSearch()
                .rowCountShouldBe(4);
    }

    @Story("Пагинация")
    @DisplayName("Изменение размера страницы и навигация работают")
    @Test
    void changePageSizeAndNavigate() {
        webTablesPage.openPage();
        for (int i = 0; i < 8; i++) {
            webTablesPage.addUser(WebTableUserFactory.randomUser());
        }

        webTablesPage
                .setPageSize(20)
                .rowCountShouldBe(11)
                .pageInfoShouldBe("1 of 1")
                .setPageSize(10)
                .rowCountShouldBe(10)
                .pageInfoShouldBe("1 of 2")
                .goToNextPage()
                .rowCountShouldBe(1)
                .pageInfoShouldBe("2 of 2")
                .goToPreviousPage()
                .rowCountShouldBe(10)
                .pageInfoShouldBe("1 of 2");
    }
}
