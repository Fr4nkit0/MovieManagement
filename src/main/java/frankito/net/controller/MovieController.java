package frankito.net.controller;

import frankito.net.dto.request.SaveMovie;
import frankito.net.dto.response.GetMovie;
import frankito.net.service.MovieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class MovieController {
    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping("/movies")
    public ResponseEntity<List<GetMovie>> findALl (){
        return ResponseEntity.ok(movieService.findALl());
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
