package management.service;

import management.dto.request.SaveRating;
import management.dto.response.GetCompleteRating;
import management.dto.response.GetMovie;
import management.dto.response.GetUser;
import management.dto.response.GetUserStatistic;
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
