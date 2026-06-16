package api.reqres.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessCodeVerification(
        String token,
        @JsonProperty("project_id") String projectId
) {
}
