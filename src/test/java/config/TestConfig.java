package config;

import com.codeborne.selenide.Configuration;

public class TestConfig {

    public static void configure() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }
}
