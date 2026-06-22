package api.demoqa;

import java.util.Locale;

public final class ApiConfig {

    private ApiConfig() {
    }

    public static String baseUrl() {
        return property("apiBaseUrl", "https://demoqa.com");
    }

    private static String property(String name, String defaultValue) {
        return System.getProperty(name, System.getenv().getOrDefault(environmentName(name), defaultValue));
    }

    private static String environmentName(String propertyName) {
        return propertyName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase(Locale.ROOT);
    }
}
