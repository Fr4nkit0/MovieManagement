package management.service.impl;

import management.dto.request.UserSearchCriteria;
import management.dto.request.SaveUser;
import management.dto.response.GetUser;
import management.exceptions.ResourceNotFoundException;
import management.mapper.UserMapper;
import management.persistence.entity.User;
import management.persistence.repository.UserRepository;
import management.service.UserService;
import management.service.validator.PasswordValidator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<GetUser> findALl(UserSearchCriteria searchCriteria, Pageable pageable) {
        Specification<User> specification = (root, query, criteriaBuilder)->{
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
    public GetUser findOneByUsername(String username) {
        User user = findOneByUsernameEntity(username);
        return UserMapper.toGetDto(user);
    }
    @Override
    public GetUser createOne(SaveUser saveUser) {
        PasswordValidator.validatePassword(saveUser.password(),saveUser.passwordRepeated());
        User createUser=UserMapper.toGetEntity(saveUser);
        return UserMapper.toGetDto(userRepository.save(createUser));
    }
    @Override
    public GetUser updateOneByUsername(SaveUser updateUser,String username) {
        PasswordValidator.validatePassword(updateUser.password(), updateUser.passwordRepeated());
        User oldUser=findOneByUsernameEntity(username);
        UserMapper.updateEntity(oldUser,updateUser);
        return UserMapper.toGetDto(oldUser);
    }
    @Override
    public void deleteOneByUsername(String username) {
        User user = findOneByUsernameEntity(username);
        userRepository.delete(user);
    }
    @Override
    public User findOneByUsernameEntity (String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("user does not exist"));
    }
}
