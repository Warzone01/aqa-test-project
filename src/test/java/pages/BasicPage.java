package pages;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BasicPage {

    // попытка убрать рекламу, чтобы элементы не прыгали, иначе тесты не стабильные
    protected void removeAds() {
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
    }

    protected void waitPageLoaded() {
        executeJavaScript("return document.readyState").equals("complete");
    }
}
