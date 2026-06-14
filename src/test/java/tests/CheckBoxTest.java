package tests;

import data.CheckBoxData;
import org.junit.jupiter.api.Test;
import pages.CheckBoxPage;

public class CheckBoxTest extends BaseTest {

    CheckBoxPage checkBoxPage = new CheckBoxPage();

    @Test
    void clickCheckBoxTest() {
        checkBoxPage
                .openPage()
                .expandFolder(CheckBoxData.homeFolder)
                .expandFolder(CheckBoxData.desktopFolder)
                .selectCheckbox(CheckBoxData.commands)
                .checkboxShouldBeSMixed(CheckBoxData.homeFolder)
                .checkboxShouldBeSMixed(CheckBoxData.desktopFolder)
                .checkboxShouldBeSelected(CheckBoxData.commands);
    }
}
