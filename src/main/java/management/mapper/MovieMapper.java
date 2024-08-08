package management.mapper;

import management.dto.request.SaveMovie;
import management.dto.response.GetMovie;
import management.dto.response.GetMovieStatistic;
import management.dto.response.GetUserStatistic;
import management.persistence.entity.Movie;

import java.util.List;

public class MovieMapper {

    public static GetMovieStatistic toGetMovieStatisticDto (Movie entity ,
                                                            int totalRatings,
                                                            double averageRating,
                                                            int lowestRating,
                                                            int highestRating){

        if (entity==null) return null;
        return new GetMovieStatistic(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                entity.getReleaseYear(),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating
        );

    }
    public static GetMovie toGetDto (Movie entity){
        if (entity==null) return null;
        int totalRatings = entity.getRatings() !=null ?
                entity.getRatings().size():0;
        return new GetMovie(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                entity.getReleaseYear(),
                totalRatings
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
