package management.application.service;

import management.application.dto.request.UserSearchCriteria;
import management.application.dto.request.SaveUser;
import management.application.dto.response.GetUser;
import management.application.dto.response.GetUserStatistic;
import management.application.exceptions.ResourceNotFoundException;
import management.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<GetUser> findALl (UserSearchCriteria searchCriteria, Pageable pageable);
    /**
     * @param username
     * @return
     * @throws {@link ResourceNotFoundException} if the given username dot not exist
     */
    GetUserStatistic findOneByUsername (String username);
    GetUser createOne (SaveUser saveUser);
    /**
     * @param username
     * @throws {@link ResourceNotFoundException} if the given username dot not exist
     * @return
     */
    GetUser updateOneByUsername (SaveUser updateUser,String username);
    void deleteOneByUsername(String username);

    /**
     * @param username
     * @throws {@link ResourceNotFoundException} if the given username dot not exist
     * @return
     */
    User findOneByUsernameEntity (String username);
}
