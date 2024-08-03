package frankito.net.service;

import frankito.net.dto.request.SaveUser;
import frankito.net.dto.request.UserSearchCriteria;
import frankito.net.dto.response.GetUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<GetUser> findALl (UserSearchCriteria searchCriteria,Pageable pageable);
    GetUser findOneById (Long id);
    GetUser createOne (SaveUser saveUser);
    GetUser updateOneById (SaveUser updateUser,Long id);
    void deleteOneById (Long id);
}
