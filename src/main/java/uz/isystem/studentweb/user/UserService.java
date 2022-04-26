package uz.isystem.studentweb.user;

import org.springframework.stereotype.Service;
import uz.isystem.studentweb.exception.UserNotFoundException;
import uz.isystem.studentweb.service.PasswordService;
import uz.isystem.studentweb.usertype.UserType;
import uz.isystem.studentweb.usertype.UserTypeDto;
import uz.isystem.studentweb.usertype.UserTypeService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserTypeService userTypeService;

    public UserService(UserRepository userRepository, UserTypeService userTypeService) {
        this.userRepository = userRepository;
        this.userTypeService = userTypeService;
    }

    // Main functions
    public UserDto get(Integer id) {
        User user = getEntity(id);
//        UserType userType = userTypeService.get(user.getUserTypeId());
//        user.setUserType(userType);

        return convertEntityToDto(user, new UserDto());
    }

    public void create(UserDto userDto) {

        // Checking...
        userTypeService.getEntity(userDto.getUserTypeId());
        User user = convertDtoToEntity(userDto, new User());
        user.setPassword(PasswordService.generateMD5(userDto.getPassword()));
        user.setStatus(true);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public List<UserDto> getAll() {
        List<User> userList = userRepository.findAllByDeletedAtIsNull();
        if (userList.isEmpty()) {
            throw new UserNotFoundException("User Not Found !");
        }
        List<UserDto> userDtoList = new LinkedList<>();
        for (User user : userList) {
            userDtoList.add(convertEntityToDto(user, new UserDto()));
        }

        return userDtoList;
    }

    public User update(Integer id, UserDto userDto) {

        // Cheking...
        userTypeService.getEntity(userDto.getUserTypeId());

        User oldUser = getEntity(id);
        convertDtoToEntity(userDto, oldUser);
        oldUser.setUpdatedAt(LocalDateTime.now());
        oldUser.setStatus(oldUser.getStatus());
        userRepository.save(oldUser);
        return oldUser;
    }

    public Boolean delete(Integer id) {
        User user = getEntity(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }


    // Secondary functions

    public User getEntity(Integer id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new UserNotFoundException("User Not Found !");
        }
        return optional.get();
    }

    public UserDto convertEntityToDto(User entity, UserDto dto) {
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setUserName(entity.getUserName());
        dto.setUserId(entity.getUserId());
        dto.setChatId(entity.getChatId());
        dto.setUserTypeId(entity.getUserTypeId());
        dto.setUserTypeDto(userTypeService.convertEntityToDto(entity.getUserType(), new UserTypeDto()));
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());

        return dto;
    }

    public User convertDtoToEntity(UserDto dto, User entity) {
        entity.setUserId(dto.getUserId());
        entity.setChatId(dto.getChatId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setUserName(dto.getUserName());
        entity.setPhone(dto.getPhone());
        entity.setUserTypeId(dto.getUserTypeId());

        return entity;
    }
}
