package frankito.net.controller;

import frankito.net.dto.request.SaveUser;
import frankito.net.dto.response.GetUser;
import frankito.net.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController("api/v1")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public ResponseEntity<List<GetUser>> findALl (){
        return ResponseEntity.ok(userService.findALl());
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<GetUser> findOneById (@PathVariable Long userId){
        return ResponseEntity.ok(userService.findOneById(userId));
    }
    @PostMapping("/user")
    public  ResponseEntity<GetUser> createOne (@RequestBody @Valid SaveUser saveUser,
                                               HttpServletRequest request){
        GetUser userCreated= userService.createOne(saveUser);
        String baseUrl=request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl+"/"+userCreated.username());
        return ResponseEntity.created(newLocation).body(userCreated);
    }
    @PutMapping("/user/{userId}) ")
    public ResponseEntity<GetUser> updateOneById (@RequestBody @Valid SaveUser updateUser,
                                                  @PathVariable Long userId){
        return ResponseEntity.ok(userService.updateOneById(updateUser,userId));
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteOneById (Long userId){
        userService.deleteOneById(userId);
        return ResponseEntity.noContent().build();
    }

}
