package management.service;

import management.dto.request.SaveRating;
import management.dto.response.GetCompleteRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingService {
    Page<GetCompleteRating> findALl (Pageable pageable);
    Page<GetCompleteRating> findALlMovieId(Long movieId,Pageable pageable);
    Page<GetCompleteRating> findALlUsername(String username,Pageable pageable);
    GetCompleteRating findById (Long ratingId);
    GetCompleteRating createOne(SaveRating saveRating);
    GetCompleteRating updateOneById (SaveRating updateRating,Long ratingId);
    void deleteOneById(Long ratingId);
}
