package frankito.net.mapper;

import frankito.net.dto.request.SaveMovie;
import frankito.net.dto.response.GetMovie;
import frankito.net.persistence.entity.Movie;

import java.util.List;

public class MovieMapper {

    public static GetMovie toGetDto (Movie entity){
        if (entity==null) return null;
        return new GetMovie(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                entity.getReleaseYear(),
                null
        );
    }
    public static List<GetMovie>  toGetDtoList (List<Movie> entities){
        if (entities==null) return null;
        return  entities.stream().map(MovieMapper::toGetDto).toList();
    }
    public static  Movie toGetEntity (SaveMovie saveEntity){
        return Movie.builder()
                .title(saveEntity.title())
                .director(saveEntity.director())
                .releaseYear(saveEntity.releaseYear())
                .genre(saveEntity.genre())
                .build();
    }
    public static void updateMovie (Movie oldMovie , SaveMovie updateMovie){
        if (oldMovie==null || updateMovie==null) return;
        oldMovie.setGenre(updateMovie.genre());
        oldMovie.setTitle(updateMovie.title());
        oldMovie.setDirector(updateMovie.director());
        oldMovie.setReleaseYear(updateMovie.releaseYear());
    }




}
