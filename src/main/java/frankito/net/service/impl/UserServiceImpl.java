package frankito.net.service.impl;

import frankito.net.dto.request.SaveUser;
import frankito.net.dto.response.GetUser;
import frankito.net.exceptions.ResourceNotFoundException;
import frankito.net.mapper.UserMapper;
import frankito.net.persistence.entity.User;
import frankito.net.persistence.repository.UserRepository;
import frankito.net.service.UserService;
import frankito.net.service.validator.PasswordValidator;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<GetUser> findALl() {
        List<User> users=userRepository.findAll();
        if (users.isEmpty())throw new ResourceNotFoundException("empty records");
        return UserMapper.toGetDtoList(users);
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
