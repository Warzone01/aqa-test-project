package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CheckBoxPage {

    private SelenideElement checkboxByName(String name) {
        return $("[aria-label='Select " + name + "']");
    }

    private SelenideElement nodeByName(String name) {
        return $x("//span[@class='rc-tree-title' and text()='" + name + "']/ancestor::div[@role='treeitem']");
    }

    private SelenideElement expandButtonByName(String name) {
        return nodeByName(name).$(".rc-tree-switcher");
    }

    @Step("Открыть страницу с чекбоксами")
    public CheckBoxPage openPage() {
        open("/checkbox");
        checkboxByName("Home").shouldBe(visible);
        return this;
    }

    @Step("Выбрать чекбокс {name}")
    public CheckBoxPage selectCheckbox(String name) {
        checkboxByName(name).click();
        return this;
    }

    @Step("Проверить, что чекбокс {name} выбран")
    public CheckBoxPage checkboxShouldBeSelected(String name) {
        checkboxByName(name).shouldHave(attribute("aria-checked", "true"));
        return this;
    }

    @Step("Проверить, что чекбокс {name} выбран частично")
    public CheckBoxPage checkboxShouldBeMixed(String name) {
        checkboxByName(name).shouldHave(attribute("aria-checked", "mixed"));
        return this;
    }

    @Step("Проверить, что чекбокс {name} не выбран")
    public CheckBoxPage checkboxShouldNotBeSelected(String name) {
        checkboxByName(name).shouldHave(attribute("aria-checked", "false"));
        return this;
    }

    @Step("Раскрыть папку {name}")
    public CheckBoxPage expandFolder(String name) {
        expandButtonByName(name).click();
        return this;
    }
}
