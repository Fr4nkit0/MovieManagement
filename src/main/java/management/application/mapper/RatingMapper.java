package management.application.mapper;

import management.application.dto.request.SaveRating;
import management.application.dto.response.GetCompleteRating;
import management.application.dto.response.GetMovie;
import management.application.dto.response.GetUser;
import management.domain.entity.Rating;

import java.util.List;


public class RatingMapper {

    public static GetCompleteRating toGetCompleteRatingDto (Rating entity){
        if (entity==null) return null;
        String username = entity.getUser()!=null ?
                entity.getUser().getUsername():null ;
        String movieTitle = entity.getMovie()!=null?
                entity.getMovie().getTitle() : null;
        return new GetCompleteRating(
                entity.getId(),
                entity.getMovieId(),
                movieTitle,
                username,
                entity.getRating()
        );
    }
    public static List<GetCompleteRating> toGetCompleteRatingDtoList (List<Rating> entities){
        if (entities==null) return null;
        return entities.stream().map(RatingMapper::toGetCompleteRatingDto).toList();
    }
    public  static Rating toEntity (SaveRating saveRating, long userId){
        if (saveRating==null) return null;
        return Rating.builder()
                .movieId(saveRating.movieId())
                .userId(userId)
                .rating(saveRating.rating())
                .build();
    }
    public static  void updateEntity (Rating oldEntity , SaveRating updateEntity , Long userId){
        if (oldEntity==null || updateEntity==null) return;
        oldEntity.setRating(updateEntity.rating());
        oldEntity.setMovieId(updateEntity.movieId());
        oldEntity.setUserId(userId);

    }


    public static GetMovie.GetRating toGetMovieRatingDto (Rating entity){
        if (entity==null) return null;
        String username = entity.getUser() != null?
                entity.getUser().getUsername(): null ;
        return new GetMovie.GetRating(
                entity.getId(),
                entity.getRating(),
                username
        );
     }

     public static GetUser.GetRating toGetUserRatingDto (Rating entity){
         if (entity==null) return null;
         String title = entity.getMovie() !=null?
                 entity.getMovie().getTitle():null;
         return new GetUser.GetRating(
                 entity.getId(),
                 title,
                 entity.getMovieId(),
                 entity.getRating()
         );

     }
    public static List<GetMovie.GetRating> toGetMovieRatingDtoList (List<Rating> entities){
        if (entities==null) return null;
        return entities.stream().map(RatingMapper::toGetMovieRatingDto).toList();
    }
     public static List<GetUser.GetRating> toGetUserRatingDtoList (List<Rating> entities){
        if (entities==null) return null;
        return entities.stream().map(RatingMapper::toGetUserRatingDto).toList();
     }
}
