package frankito.net.service;

import frankito.net.dto.request.SaveUser;
import frankito.net.dto.response.GetUser;

import java.util.List;

public interface UserService {
    List<GetUser> findALl ();
    GetUser findOneById (Long id);
    GetUser createOne (SaveUser saveUser);
    GetUser updateOneById (SaveUser updateUser,Long id);
    void deleteOneById (Long id);
}
