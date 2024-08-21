package management.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import management.domain.util.MovieGenre;

import java.io.Serializable;

public record GetMovie(
        long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year")
        int releaseYear,
        int totalRatings
) implements Serializable {
    public static record  GetRating(
            long id,
            int rating,
            String username
    )implements Serializable {}
}
