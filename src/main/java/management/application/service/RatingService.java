package management.application.service;

import management.application.dto.request.SaveRating;
import management.application.dto.response.GetCompleteRating;
import management.application.dto.response.GetMovie;
import management.application.dto.response.GetUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingService {
    Page<GetCompleteRating> findALl (Pageable pageable);
    Page<GetMovie.GetRating> findALlMovieId(Long movieId, Pageable pageable);
    Page<GetUser.GetRating> findALlUsername(String username, Pageable pageable);
    GetCompleteRating findById (Long ratingId);
    GetCompleteRating createOne(SaveRating saveRating);
    GetCompleteRating updateOneById (SaveRating updateRating,Long ratingId);
    void deleteOneById(Long ratingId);
}
