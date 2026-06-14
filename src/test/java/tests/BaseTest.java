package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import config.TestConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class BaseTest {

    private static boolean allureListenerRegistered;

    @BeforeAll
    static synchronized void setUp() {
        if (!allureListenerRegistered) {
            SelenideLogger.addListener(
                    "AllureSelenide",
                    new AllureSelenide()
                            .screenshots(true)
                            .savePageSource(true)
            );
            allureListenerRegistered = true;
        }

        TestConfig.configure();
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
