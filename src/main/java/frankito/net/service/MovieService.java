package frankito.net.service;

import frankito.net.dto.request.MovieSearchCriteria;
import frankito.net.dto.request.SaveMovie;
import frankito.net.dto.response.GetMovie;

import java.util.List;

public interface MovieService {
    List<GetMovie> findALl (MovieSearchCriteria searchCriteria);
    GetMovie findOneById (Long id);
    GetMovie createOne (SaveMovie saveMovie);
    GetMovie updateOneById (SaveMovie updateMovie,Long id);
    void deleteOneById (Long id);
}
