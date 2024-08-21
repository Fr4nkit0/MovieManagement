package management.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record ApiResponse(
        @JsonProperty("http_code") int httpCode,
        String url,
        @JsonProperty("http_method") String httpMethod,
        String message,
        @JsonProperty("backend_message") String backendMessage,
        @JsonProperty("time_tamp") LocalDateTime timeTamp,
        List<String> details
) {
}
