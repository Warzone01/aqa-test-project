package api.reqres.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessRequest(
        String email,
        @JsonProperty("project_id") String projectId
) {
}
