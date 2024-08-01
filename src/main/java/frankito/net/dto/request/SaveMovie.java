package frankito.net.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import frankito.net.util.MovieGenre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record SaveMovie(
        @Size(min = 4 , max = 255) @NotBlank
        String title,
        @Size(min = 4, max = 255) @NotBlank
        String director,
        MovieGenre genre,
        @Min (value = 1900) @JsonProperty(value = "release_year")
        int releaseYear
) implements Serializable {
}
