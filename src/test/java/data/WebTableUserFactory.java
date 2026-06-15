package data;

import java.util.concurrent.ThreadLocalRandom;

public final class WebTableUserFactory {

    private static final String[] FIRST_NAMES = {
            "Olivia", "James", "Sophia", "Daniel", "Emma", "Lucas", "Mia", "Henry"
    };
    private static final String[] LAST_NAMES = {
            "Adams", "Baker", "Clark", "Davis", "Evans", "Foster", "Green", "Harris"
    };
    private static final String[] DEPARTMENTS = {
            "Engineering", "Finance", "Legal", "Marketing", "Operations", "Sales"
    };

    private WebTableUserFactory() {
    }

    public static WebTableUser randomUser() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String firstName = randomValue(FIRST_NAMES, random);
        String lastName = randomValue(LAST_NAMES, random);
        String uniqueSuffix = Long.toUnsignedString(random.nextLong(), 36);

        return new WebTableUser(
                firstName,
                lastName,
                (firstName + "." + lastName + "." + uniqueSuffix + "@example.com").toLowerCase(),
                random.nextInt(18, 100),
                random.nextInt(30_000, 500_001),
                randomValue(DEPARTMENTS, random)
        );
    }

    private static String randomValue(String[] values, ThreadLocalRandom random) {
        return values[random.nextInt(values.length)];
    }
}
