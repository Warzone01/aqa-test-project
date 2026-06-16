package api.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record User(@JsonAlias("userId") String userID, String username, List<Book> books) {
}
