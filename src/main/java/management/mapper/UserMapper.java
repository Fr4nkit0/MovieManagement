package management.mapper;

import management.dto.request.SaveUser;
import management.dto.response.GetUser;
import management.persistence.entity.User;

import java.util.List;

public class UserMapper {
    public static GetUser toGetDto (User entity){
        if (entity==null) return null;
        int totalRatings = entity.getRatings() !=null ?
                entity.getRatings().size():0;
        return new GetUser(
                entity.getName(),
                entity.getUsername(),
                totalRatings
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
