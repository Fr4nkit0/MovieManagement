package management.controller;

import jakarta.servlet.http.HttpServletRequest;
import management.dto.request.SaveRating;
import management.dto.response.GetCompleteRating;
import management.service.RatingService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController("api/v1")
public class RatingController {
    private final RatingService ratingService;
    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/ratings")
    public ResponseEntity<Page<GetCompleteRating>>findALl (Pageable pageable){
        return ResponseEntity.ok(ratingService.findALl(pageable));
    }
    @GetMapping("/rating/{ratingId}")
    public ResponseEntity<GetCompleteRating> findById (@PathVariable Long ratingId){
        return ResponseEntity.ok(ratingService.findById(ratingId));
    }
    @PostMapping("/rating")
    public ResponseEntity<GetCompleteRating> createOne (@RequestBody SaveRating saveRating,
                                                        HttpServletRequest request){
        GetCompleteRating rating = ratingService.createOne(saveRating);

        String urlRequest = request.getRequestURL().toString();
        URI urlCreated = URI.create(urlRequest + "/" + rating.ratingId());
        return ResponseEntity.created(urlCreated).body(rating);
    }
    @PutMapping("/rating/{ratingId}")
    public ResponseEntity<GetCompleteRating> updateById (@RequestBody SaveRating updateRating,
                                                         @PathVariable Long ratingId){
        return ResponseEntity.ok(ratingService.updateOneById(updateRating,ratingId));
    }
    @DeleteMapping("/rating/{ratingId}")
    public ResponseEntity<Void> deleteOneById (@PathVariable Long ratingId){
        ratingService.deleteOneById(ratingId);
        return  ResponseEntity.noContent().build();
    }

}
