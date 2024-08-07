package management.service;

import management.dto.request.MovieSearchCriteria;
import management.dto.request.SaveMovie;
import management.dto.response.GetMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MovieService {
    Page<GetMovie> findALl (MovieSearchCriteria searchCriteria, Pageable pageable);
    GetMovie findOneById (Long id);
    GetMovie createOne (SaveMovie saveMovie);
    GetMovie updateOneById (SaveMovie updateMovie,Long id);
    void deleteOneById (Long id);
}
