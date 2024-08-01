package frankito.net.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public record GetUser (
        String name,
        String username,
        List<GetRating> ratings
) implements Serializable {
    public static  record GetRating (
            long id,
            @JsonProperty(value = "movie_title")
            String movieTitle,
            @JsonProperty(value = "movie_id")
            long movieId,
            int rating

    ) implements Serializable{}
}
