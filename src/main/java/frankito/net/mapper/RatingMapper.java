package frankito.net.mapper;

import frankito.net.dto.response.GetMovie;
import frankito.net.dto.response.GetUser;
import frankito.net.persistence.entity.Rating;


public class RatingMapper {

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
}
