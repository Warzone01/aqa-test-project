package tests.api;

import api.BookStoreClient;
import api.model.Book;
import api.model.Books;
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
@Tag("regression")
public class BooksApiTest {

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
        Book catalogBook = bookStoreClient.getBooksSuccessfully().books().get(0);

        bookStoreClient.getBook(catalogBook.isbn())
                .then()
                .statusCode(200)
                .body("isbn", equalTo(catalogBook.isbn()))
                .body("title", equalTo(catalogBook.title()))
                .body("author", equalTo(catalogBook.author()));
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
