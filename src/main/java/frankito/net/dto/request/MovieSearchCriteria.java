package frankito.net.dto.request;

import frankito.net.util.MovieGenre;

public record MovieSearchCriteria(
        String title,
        Integer minReleaseYear,
        Integer maxReleaseYear,
        Integer minAverageRating
) {
}
