package config;

import com.codeborne.selenide.Configuration;

import java.util.Locale;

public final class TestConfig {

    private TestConfig() {
    }

    public static void configure() {
        Configuration.baseUrl = property("baseUrl", "https://demoqa.com");
        Configuration.browser = property("browser", "chrome");
        Configuration.browserSize = property("browserSize", "1920x1080");
        Configuration.timeout = Long.parseLong(property("timeout", "10000"));
        Configuration.headless = Boolean.parseBoolean(property("headless", "false"));
        Configuration.remote = optionalProperty("remoteUrl");
    }

    private static String property(String name, String defaultValue) {
        return System.getProperty(name, System.getenv().getOrDefault(environmentName(name), defaultValue));
    }

    private static String optionalProperty(String name) {
        String value = System.getProperty(name, System.getenv(environmentName(name)));
        return value == null || value.isBlank() ? null : value;
    }

    private static String environmentName(String propertyName) {
        return propertyName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase(Locale.ROOT);
    }
}
