package api.model;

public record Token(String token, String expires, String status, String result) {
}
