package frankito.net.controller;

import frankito.net.dto.request.MovieSearchCriteria;
import frankito.net.dto.request.SaveMovie;
import frankito.net.dto.response.GetMovie;
import frankito.net.service.MovieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("api/v1")
public class MovieController {
    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping("/movies")
    public ResponseEntity<Page<GetMovie>> findALl (@RequestParam(required = false) String title,
                                                   @RequestParam(required = false, name = "min_release_year") Integer minReleaseYear,
                                                   @RequestParam(required = false, name = "max_release_year") Integer maxReleaseYear,
                                                   @RequestParam(required = false, name = "min_average_rating")Integer minAverageRating,
                                                   Pageable pageable){
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title,minReleaseYear,
                maxReleaseYear,minAverageRating);
        return ResponseEntity.ok(movieService.findALl(searchCriteria,pageable));
    }
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<GetMovie> findOne (@PathVariable Long movieId){
        return ResponseEntity.ok(movieService.findOneById(movieId));
    }
    @PostMapping("/movie")
    public ResponseEntity<GetMovie> createOne (@Valid @RequestBody SaveMovie saveMovie,
                                               HttpServletRequest request){
        GetMovie movieCreated = movieService.createOne(saveMovie);
        String baseURl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseURl+"/"+movieCreated.id());
        return ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }
    @PutMapping ("/movie/{movieId}")
    public ResponseEntity<GetMovie> updateOne (@RequestBody @Valid SaveMovie updateMovie,
                                               @PathVariable Long movieId) {

    return ResponseEntity.ok(movieService.updateOneById(updateMovie,movieId));
    }
    @DeleteMapping(value = "/movie/{movieId}")
    public ResponseEntity<Void> deleteOne(@PathVariable Long movieId){
        movieService.deleteOneById(movieId);
        return ResponseEntity.noContent().build();
    }







}
