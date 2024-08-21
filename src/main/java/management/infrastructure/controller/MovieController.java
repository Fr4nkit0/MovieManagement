package management.infrastructure.controller;

import management.application.dto.request.MovieSearchCriteria;
import management.application.dto.request.SaveMovie;
import management.application.dto.response.GetMovie;
import management.application.dto.response.GetMovieStatistic;
import management.application.service.MovieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import management.application.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final RatingService ratingService;

    @Autowired
    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<Page<GetMovie>> findALl (@RequestParam(required = false) String title,
                                                   @RequestParam(required = false, name = "min_release_year") Integer minReleaseYear,
                                                   @RequestParam(required = false, name = "max_release_year") Integer maxReleaseYear,
                                                   @RequestParam(required = false, name = "min_average_rating")Integer minAverageRating,
                                                   Pageable pageable){
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title,minReleaseYear,
                maxReleaseYear,minAverageRating);
        return ResponseEntity.ok(movieService.findALl(searchCriteria,pageable));
    }
    @GetMapping("/{movieId}/ratings")
    public ResponseEntity<Page<GetMovie.GetRating>>  findAllRatingsForMovieById (@PathVariable Long movieId,
                                                                                 Pageable pageable){
        return ResponseEntity.ok(ratingService.findALlMovieId(movieId,pageable));
    }
    @GetMapping("/{movieId}")
    public ResponseEntity<GetMovieStatistic> findOne (@PathVariable Long movieId){
        return ResponseEntity.ok(movieService.findOneById(movieId));
    }
    @PostMapping
    public ResponseEntity<GetMovie> createOne (@Valid @RequestBody SaveMovie saveMovie,
                                               HttpServletRequest request){
        GetMovie movieCreated = movieService.createOne(saveMovie);
        String baseURl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseURl+"/"+movieCreated.id());
        return ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }
    @PutMapping ("/{movieId}")
    public ResponseEntity<GetMovie> updateOne (@RequestBody @Valid SaveMovie updateMovie,
                                               @PathVariable Long movieId) {

    return ResponseEntity.ok(movieService.updateOneById(updateMovie,movieId));
    }
    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteOne(@PathVariable Long movieId){
        movieService.deleteOneById(movieId);
        return ResponseEntity.noContent().build();
    }







}
