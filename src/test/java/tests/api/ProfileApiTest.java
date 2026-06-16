package tests.api;

import api.model.Book;
import api.model.BookCollection;
import api.model.Isbn;
import api.model.User;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("DemoQA API: профиль")
@DisplayName("API профиля")
@Tag("api")
@Tag("regression")
public class ProfileApiTest extends BaseApiTest {

    @Story("Профиль пользователя")
    @DisplayName("Профиль нового пользователя не содержит книг")
    @Test
    @Tag("smoke")
    void newUserProfileIsEmpty() {
        User profile = accountClient.getUser(userId, token)
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);

        assertAll(
                () -> assertEquals(userId, profile.userID()),
                () -> assertEquals(credentials.userName(), profile.username()),
                () -> assertTrue(profile.books().isEmpty())
        );
    }

    @Story("Коллекция книг пользователя")
    @DisplayName("Книгу можно добавить в профиль и удалить из него")
    @Test
    void bookCanBeAddedToAndRemovedFromProfile() {
        Book book = bookStoreClient.getBooksSuccessfully().books().get(0);
        BookCollection collection = new BookCollection(userId, List.of(new Isbn(book.isbn())));

        bookStoreClient.addBooks(collection, token)
                .then()
                .statusCode(201);

        User profileWithBook = accountClient.getUser(userId, token)
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);
        assertEquals(List.of(book.isbn()), profileWithBook.books().stream().map(Book::isbn).toList());

        bookStoreClient.deleteAllBooks(userId, token)
                .then()
                .statusCode(204);

        User emptyProfile = accountClient.getUser(userId, token)
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);
        assertTrue(emptyProfile.books().isEmpty());
    }

    @Story("Авторизация профиля")
    @DisplayName("Профиль нельзя получить без токена")
    @Test
    @Tag("negative")
    void profileCannotBeReadWithoutToken() {
        accountClient.getUser(userId, "invalid-token")
                .then()
                .statusCode(401);
    }
}
