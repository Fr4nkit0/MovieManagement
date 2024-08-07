package management.dto.request;

public record MovieSearchCriteria(
        String title,
        Integer minReleaseYear,
        Integer maxReleaseYear,
        Integer minAverageRating
) {
}
