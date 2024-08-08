package management.controller;

import management.dto.request.UserSearchCriteria;
import management.dto.request.SaveUser;
import management.dto.response.GetUser;
import management.dto.response.GetUserStatistic;
import management.service.RatingService;
import management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RatingService ratingService;

    @Autowired
    public UserController(UserService userService, RatingService ratingService) {
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<Page<GetUser>> findALl (@RequestParam(required = false )String name,
                                                  @RequestParam(required = false )String username,
                                                  Pageable pageable){
        UserSearchCriteria searchCriteria = new UserSearchCriteria(name,username);
        return ResponseEntity.ok(userService.findALl(searchCriteria ,pageable));
    }
    @GetMapping("/{username}/ratings")
    public ResponseEntity<Page<GetUser.GetRating>> findALlRatingsForUsername (@PathVariable  String username,
                                                                              Pageable pageable){
        return ResponseEntity.ok(ratingService.findALlUsername(username,pageable));
    }
    @GetMapping("/{username}")
    public ResponseEntity<GetUserStatistic> findOneByUsername (@PathVariable String username){
        return ResponseEntity.ok(userService.findOneByUsername(username));
    }
    @PostMapping
    public  ResponseEntity<GetUser> createOne (@RequestBody @Valid SaveUser saveUser,
                                               HttpServletRequest request){
        GetUser userCreated= userService.createOne(saveUser);
        String baseUrl=request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl+"/"+userCreated.username());
        return ResponseEntity.created(newLocation).body(userCreated);
    }
    @PutMapping("/{username} ")
    public ResponseEntity<GetUser> updateOneByUsername (@RequestBody @Valid SaveUser updateUser,
                                                  @PathVariable String username){
        return ResponseEntity.ok(userService.updateOneByUsername(updateUser,username));
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteOneByUsername (String username){
        userService.deleteOneByUsername(username);
        return ResponseEntity.noContent().build();
    }

}
