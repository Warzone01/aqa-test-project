package tests;

import data.CheckBoxData;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.CheckBoxPage;

@Feature("Чекбоксы")
@DisplayName("Проверка дерева чекбоксов")
@Tag("regression")
public class CheckBoxTest extends BaseTest {

    private final CheckBoxPage checkBoxPage = new CheckBoxPage();

    @Story("Выбор дочернего элемента")
    @DisplayName("Родительские чекбоксы получают частично выбранное состояние")
    @Test
    @Tag("smoke")
    void clickCheckBoxTest() {
        checkBoxPage
                .openPage()
                .expandFolder(CheckBoxData.HOME_FOLDER)
                .expandFolder(CheckBoxData.DESKTOP_FOLDER)
                .selectCheckbox(CheckBoxData.COMMANDS)
                .checkboxShouldBeMixed(CheckBoxData.HOME_FOLDER)
                .checkboxShouldBeMixed(CheckBoxData.DESKTOP_FOLDER)
                .checkboxShouldBeSelected(CheckBoxData.COMMANDS)
                .checkboxShouldNotBeSelected(CheckBoxData.NOTES)
                .resultShouldNotHaveText(CheckBoxData.NOTES)
                .resultShouldNotHaveText(CheckBoxData.HOME_FOLDER)
                .resultShouldHaveText(CheckBoxData.COMMANDS);
    }

    @Story("Выбор родительского элемента")
    @DisplayName("Появление родительской папки при выборе всех дочерних элементов")
    @Test
    @Tag("regression")
    void selectParentCheckBox() {
        checkBoxPage
                .openPage()
                .expandFolder(CheckBoxData.HOME_FOLDER)
                .expandFolder(CheckBoxData.DESKTOP_FOLDER)
                .selectCheckbox(CheckBoxData.HOME_FOLDER)
                .checkboxShouldBeSelected(CheckBoxData.HOME_FOLDER)
                .checkboxShouldBeSelected(CheckBoxData.DESKTOP_FOLDER)
                .checkboxShouldBeSelected(CheckBoxData.COMMANDS)
                .checkboxShouldBeSelected(CheckBoxData.NOTES)
                .resultShouldHaveText(CheckBoxData.HOME_FOLDER)
                .resultShouldHaveText(CheckBoxData.DESKTOP_FOLDER)
                .resultShouldHaveText(CheckBoxData.COMMANDS)
                .resultShouldHaveText(CheckBoxData.NOTES);
    }
}
