package management.controller;

import management.dto.request.UserSearchCriteria;
import management.dto.request.SaveUser;
import management.dto.response.GetUser;
import management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController("api/v1")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public ResponseEntity<Page<GetUser>> findALl (@RequestParam(required = false )String name,
                                                  @RequestParam(required = false )String username,
                                                  Pageable pageable){
        UserSearchCriteria searchCriteria = new UserSearchCriteria(name,username);
        return ResponseEntity.ok(userService.findALl(searchCriteria ,pageable));
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<GetUser> findOneByUsername (@PathVariable String username){
        return ResponseEntity.ok(userService.findOneByUsername(username));
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
    public ResponseEntity<GetUser> updateOneByUsername (@RequestBody @Valid SaveUser updateUser,
                                                  @PathVariable String username){
        return ResponseEntity.ok(userService.updateOneByUsername(updateUser,username));
    }
    @DeleteMapping("/user/{username}")
    public ResponseEntity<Void> deleteOneByUsername (String username){
        userService.deleteOneByUsername(username);
        return ResponseEntity.noContent().build();
    }

}
