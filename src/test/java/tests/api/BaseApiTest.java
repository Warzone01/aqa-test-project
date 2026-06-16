package tests.api;

import api.AccountClient;
import api.BookStoreClient;
import api.model.Credentials;
import api.model.Token;
import api.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

public abstract class BaseApiTest {

    protected final AccountClient accountClient = new AccountClient();
    protected final BookStoreClient bookStoreClient = new BookStoreClient();

    protected Credentials credentials;
    protected String userId;
    protected String token;

    @BeforeEach
    void createTestUser() {
        credentials = new Credentials(
                "aqa_" + UUID.randomUUID().toString().substring(0, 8),
                "Test!12345"
        );

        User user = accountClient.createUserSuccessfully(credentials);
        Token generatedToken = accountClient.generateTokenSuccessfully(credentials);

        userId = user.userID();
        token = generatedToken.token();
    }

    @AfterEach
    void deleteTestUser() {
        if (userId != null && token != null) {
            accountClient.deleteUser(userId, token)
                    .then()
                    .statusCode(204);
        }
    }
}
