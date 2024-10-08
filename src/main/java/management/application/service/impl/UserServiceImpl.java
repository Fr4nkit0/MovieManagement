package management.application.service.impl;

import management.application.dto.request.UserSearchCriteria;
import management.application.dto.request.SaveUser;
import management.application.dto.response.GetUser;
import management.application.dto.response.GetUserStatistic;
import management.application.exceptions.ResourceNotFoundException;
import management.application.mapper.UserMapper;
import management.domain.entity.User;
import management.domain.repository.RatingRepository;
import management.domain.repository.UserRepository;
import management.application.service.UserService;
import management.application.service.validator.PasswordValidator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    public UserServiceImpl(UserRepository userRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    @Override
    public GetUserStatistic findOneByUsername(String username) {
        User user = findOneByUsernameEntity(username);
        return UserMapper.toGetUserStaticDto(user,
                ratingRepository.countByUsername(username),
                ratingRepository.avgRatingByUsername(username),
                ratingRepository.minRatingByUsername(username),
                ratingRepository.maxRatingByUsername(username)
                );
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
    @Transactional(readOnly = true)
    @Override
    public User findOneByUsernameEntity (String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("user does not exist"));
    }
}
