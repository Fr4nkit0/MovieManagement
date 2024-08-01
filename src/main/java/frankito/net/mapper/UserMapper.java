package frankito.net.mapper;

import frankito.net.dto.request.SaveUser;
import frankito.net.dto.response.GetUser;
import frankito.net.persistence.entity.Movie;
import frankito.net.persistence.entity.User;

import java.util.List;

public class UserMapper {
    public static GetUser toGetDto (User entity){
        if (entity==null) return null;
        return new GetUser(
                entity.getName(),
                entity.getUsername(),
                null
        );
    }
    public static List<GetUser> toGetDtoList (List<User> entities){
        if (entities==null) return null;
        return entities.stream().map(UserMapper::toGetDto).toList();
    }
    public static User toGetEntity (SaveUser saveEntity){
        if (saveEntity==null) return null;
        return User.builder()
                .username(saveEntity.username())
                .name(saveEntity.name())
                .password(saveEntity.password())
                .build();
    }
    public static void updateEntity (User oldUser, SaveUser updateUser){
        if (oldUser==null||updateUser==null) return;
        oldUser.setName(updateUser.name());
        oldUser.setPassword(updateUser.password());
    }

}
