package frankito.net.service.impl;

import frankito.net.dto.request.SaveUser;
import frankito.net.dto.request.UserSearchCriteria;
import frankito.net.dto.response.GetUser;
import frankito.net.exceptions.ResourceNotFoundException;
import frankito.net.mapper.UserMapper;
import frankito.net.persistence.entity.User;
import frankito.net.persistence.repository.UserRepository;
import frankito.net.service.UserService;
import frankito.net.service.validator.PasswordValidator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<GetUser> findALl(UserSearchCriteria searchCriteria,Pageable pageable) {
        Specification<User> specification = (root,query,criteriaBuilder)->{
            List<Predicate> predicates= new ArrayList<>();
            if (StringUtils.hasText(searchCriteria.name())){
                Predicate nameLike=criteriaBuilder.like(root.get("name"),"%".concat(searchCriteria.name()).concat("%"));
                predicates.add(nameLike);

            }
            if (StringUtils.hasText(searchCriteria.username())){
                Predicate usernameLike=criteriaBuilder.like(root.get("username"),
                        "%".concat(searchCriteria.username()).concat("%")  );
                predicates.add(usernameLike);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        } ;
        Page<User> users=userRepository.findAll(specification,pageable);
        if (users.isEmpty())throw new ResourceNotFoundException("empty records");
        return users.map(UserMapper::toGetDto);
    }
    @Override
    public GetUser findOneById(Long id) {
        User user = findOneByIdEntity(id);
        return UserMapper.toGetDto(user);
    }
    @Override
    public GetUser createOne(SaveUser saveUser) {
        PasswordValidator.validatePassword(saveUser.password(),saveUser.passwordRepeated());
        User createUser=UserMapper.toGetEntity(saveUser);
        return UserMapper.toGetDto(userRepository.save(createUser));
    }
    @Override
    public GetUser updateOneById(SaveUser updateUser,Long id) {
        PasswordValidator.validatePassword(updateUser.password(), updateUser.passwordRepeated());
        User oldUser=findOneByIdEntity(id);
        UserMapper.updateEntity(oldUser,updateUser);
        return UserMapper.toGetDto(oldUser);
    }
    @Override
    public void deleteOneById(Long id) {
        User user = findOneByIdEntity(id);
        userRepository.delete(user);
    }
    private User findOneByIdEntity (Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("The id does not exist"+id));
    }
}
