package frankito.net.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import frankito.net.util.MovieGenre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

public record SaveMovie(
        @Size(min = 4 , max = 255 ,message = "{generic.size}")
        @NotBlank(message = "{generic.notBlank}")
        String title,
        @Size(min = 4, max = 255 ,message = "{generic.size}")
        @NotBlank(message = "generic.notBlank")
        String director,
        MovieGenre genre,
        @Min (value = 1900, message = "{generic.min}")
        @JsonProperty(value = "release_year")
        int releaseYear
) implements Serializable{
}
