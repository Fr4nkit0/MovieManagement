package frankito.net.service.impl;

import frankito.net.dto.request.MovieSearchCriteria;
import frankito.net.dto.request.SaveMovie;
import frankito.net.dto.response.GetMovie;
import frankito.net.exceptions.ResourceNotFoundException;
import frankito.net.mapper.MovieMapper;
import frankito.net.persistence.entity.Movie;
import frankito.net.persistence.repository.MovieRepository;
import frankito.net.persistence.specification.FindAllMoviesSpecification;
import frankito.net.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Page<GetMovie> findALl(MovieSearchCriteria searchCriteria, Pageable pageable) {
        FindAllMoviesSpecification moviesSpecification = new FindAllMoviesSpecification(searchCriteria);
        Page<Movie> movies = movieRepository.findAll(moviesSpecification,pageable);
        if(movies.isEmpty()) throw new ResourceNotFoundException("empty records");
        return movies.map(MovieMapper::toGetDto);
    }

    @Override
    public GetMovie findOneById(Long id) {
        return MovieMapper.toGetDto(find0neByIdEntity(id));
    }

    @Override
    public GetMovie createOne(SaveMovie saveMovie) {
        Movie createMovie=MovieMapper.toGetEntity(saveMovie);
        return MovieMapper.toGetDto(movieRepository.save(createMovie));
    }

    @Override
    public GetMovie updateOneById(SaveMovie updateMovie,Long id) {
        Movie oldMovie = find0neByIdEntity(id);
        MovieMapper.updateMovie(oldMovie,updateMovie);
        return MovieMapper.toGetDto(movieRepository.save(oldMovie));
    }

    @Override
    public void deleteOneById(Long id) {
        Movie deleteMovie = find0neByIdEntity(id);
        movieRepository.delete(deleteMovie);
    }
    private Movie find0neByIdEntity (Long id){
        return movieRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("The id does not exist"+id));
    }
}
