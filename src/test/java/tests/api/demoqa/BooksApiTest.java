package tests.api.demoqa;

import api.demoqa.BookStoreClient;
import api.demoqa.model.Books;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("DemoQA API: книги")
@DisplayName("API книг")
@Tag("api")
@Tag("demoqa")
@Tag("regression")
public class BooksApiTest {

    private static final String EXISTING_ISBN = "9781449325862";
    private static final String EXISTING_TITLE = "Git Pocket Guide";
    private static final String EXISTING_AUTHOR = "Richard E. Silverman";

    private final BookStoreClient bookStoreClient = new BookStoreClient();

    @Story("Каталог книг")
    @DisplayName("Каталог содержит полные данные о книгах")
    @Test
    @Tag("smoke")
    void catalogContainsCompleteBookRecords() {
        Books catalog = bookStoreClient.getBooksSuccessfully();

        assertFalse(catalog.books().isEmpty(), "Каталог книг не должен быть пустым");
        catalog.books().forEach(book -> assertAll(
                () -> assertFalse(book.isbn().isBlank()),
                () -> assertFalse(book.title().isBlank()),
                () -> assertFalse(book.author().isBlank()),
                () -> assertNotNull(book.pages()),
                () -> assertTrue(book.pages() > 0)
        ));
    }

    @Story("Данные книги")
    @DisplayName("Книгу можно найти по ISBN из каталога")
    @Test
    void bookCanBeFoundByIsbn() {
        bookStoreClient.getBook(EXISTING_ISBN)
                .then()
                .statusCode(200)
                .body("isbn", equalTo(EXISTING_ISBN))
                .body("title", equalTo(EXISTING_TITLE))
                .body("author", equalTo(EXISTING_AUTHOR));
    }

    @Story("Данные книги")
    @DisplayName("Неизвестный ISBN возвращает ошибку валидации")
    @Test
    @Tag("negative")
    void unknownIsbnReturnsValidationError() {
        bookStoreClient.getBook("0000000000000")
                .then()
                .statusCode(400)
                .body("code", equalTo("1205"))
                .body("message", equalTo("ISBN supplied is not available in Books Collection!"));
    }
}
