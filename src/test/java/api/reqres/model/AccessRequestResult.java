package api.reqres.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessRequestResult(
        AccessRequestData data
) {

    public record AccessRequestData(
            Boolean sent,
            String token,
            @JsonProperty("magic_link") String magicLink,
            @JsonProperty("expires_in_minutes") Integer expiresInMinutes,
            String message
    ) {
    }
}
