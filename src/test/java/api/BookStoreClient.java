package api;

import api.model.BookCollection;
import api.model.Books;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.ApiSpecifications.authorizedRequest;
import static api.ApiSpecifications.jsonResponse;
import static api.ApiSpecifications.request;
import static io.restassured.RestAssured.given;

public class BookStoreClient {

    @Step("Получить каталог книг")
    public Response getBooks() {
        return given()
                .spec(request())
                .when()
                .get("/BookStore/v1/Books");
    }

    @Step("Получить книгу по ISBN: {isbn}")
    public Response getBook(String isbn) {
        return given()
                .spec(request())
                .queryParam("ISBN", isbn)
                .when()
                .get("/BookStore/v1/Book");
    }

    @Step("Добавить книги в коллекцию пользователя")
    public Response addBooks(BookCollection collection, String token) {
        return given()
                .spec(authorizedRequest(token))
                .body(collection)
                .when()
                .post("/BookStore/v1/Books");
    }

    @Step("Удалить все книги из коллекции пользователя")
    public Response deleteAllBooks(String userId, String token) {
        return given()
                .spec(authorizedRequest(token))
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books");
    }

    public Books getBooksSuccessfully() {
        return getBooks()
                .then()
                .spec(jsonResponse())
                .statusCode(200)
                .extract()
                .as(Books.class);
    }
}
