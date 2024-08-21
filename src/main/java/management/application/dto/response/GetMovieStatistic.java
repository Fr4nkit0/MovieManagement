package management.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import management.domain.util.MovieGenre;

import java.io.Serializable;

public record GetMovieStatistic (
        Long id,
        String title,
        String  director,
        MovieGenre genre,
        @JsonProperty("release_year") int releaseYear,
        @JsonProperty("total_ratings")int totalRatings,
        @JsonProperty("average_ratings")Double averageRatings,
        @JsonProperty("lowest_rating")int lowestRating,
        @JsonProperty("highest_ratings")int highestRating
) implements Serializable {

    @Override
    public Double averageRatings() {
        return Double.parseDouble(String.format("%1.2f", averageRatings));
    }
}
