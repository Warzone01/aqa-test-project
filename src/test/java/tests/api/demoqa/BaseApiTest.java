package tests.api.demoqa;

import api.demoqa.AccountClient;
import api.demoqa.BookStoreClient;
import api.demoqa.model.Credentials;
import api.demoqa.model.Token;
import api.demoqa.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseApiTest {

    protected final AccountClient accountClient = new AccountClient();
    protected final BookStoreClient bookStoreClient = new BookStoreClient();

    protected Credentials credentials;
    protected String userId;
    protected String token;

    @BeforeAll
    void createTestUser() {
        credentials = new Credentials(
                "aqa_" + UUID.randomUUID().toString().substring(0, 8),
                "Test!12345"
        );

        User user = accountClient.createUserForSetupSuccessfully(credentials);
        Token generatedToken = accountClient.generateTokenForSetupSuccessfully(credentials);

        userId = user.userID();
        token = generatedToken.token();
    }

    @AfterAll
    void deleteTestUser() {
        if (userId != null && token != null) {
            accountClient.deleteUserForSetup(userId, token)
                    .then()
                    .statusCode(204);
        }
    }
}
