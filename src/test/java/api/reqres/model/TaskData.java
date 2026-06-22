package api.reqres.model;

public record TaskData(
        String title,
        String notes,
        Boolean completed
) {
}
