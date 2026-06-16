package api.model;

import java.util.List;

public record BookCollection(String userId, List<Isbn> collectionOfIsbns) {
}
