package management.application.service;

import management.application.dto.request.MovieSearchCriteria;
import management.application.dto.request.SaveMovie;
import management.application.dto.response.GetMovie;
import management.application.dto.response.GetMovieStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MovieService {
    Page<GetMovie> findALl (MovieSearchCriteria searchCriteria, Pageable pageable);
    GetMovieStatistic findOneById (Long id);
    GetMovie createOne (SaveMovie saveMovie);
    GetMovie updateOneById (SaveMovie updateMovie,Long id);
    void deleteOneById (Long id);
}
