package api.reqres;

import java.util.Locale;

public final class ReqresConfig {

    private static final String DEFAULT_BASE_URL = "https://reqres.in";
    private static final String DEFAULT_PROJECT_ID = "1737";
    private static final String DEFAULT_PUBLIC_API_KEY = "pub_f9840a22454d42e631677cd094b106d8";
    private static final String DEFAULT_MANAGE_API_KEY = "pro_e38798f8d10fe3a417db33fc616d084d49bf332c205c1450";
    private static final String DEFAULT_COLLECTION_SLUG = "todos";

    private ReqresConfig() {
    }

    public static String baseUrl() {
        return property("reqresBaseUrl", DEFAULT_BASE_URL);
    }

    public static String projectId() {
        return property("reqresProjectId", DEFAULT_PROJECT_ID);
    }

    public static String publicApiKey() {
        return property("reqresPublicApiKey", DEFAULT_PUBLIC_API_KEY);
    }

    public static String manageApiKey() {
        return property("reqresManageApiKey", DEFAULT_MANAGE_API_KEY);
    }

    public static String collectionSlug() {
        return property("reqresCollectionSlug", DEFAULT_COLLECTION_SLUG);
    }

    private static String property(String name, String defaultValue) {
        return System.getProperty(name, System.getenv().getOrDefault(environmentName(name), defaultValue));
    }

    private static String environmentName(String propertyName) {
        return propertyName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase(Locale.ROOT);
    }
}
