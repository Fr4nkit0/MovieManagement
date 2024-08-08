package management.service;

import management.dto.request.UserSearchCriteria;
import management.dto.request.SaveUser;
import management.dto.response.GetUser;
import management.dto.response.GetUserStatistic;
import management.exceptions.ResourceNotFoundException;
import management.persistence.entity.User;
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
