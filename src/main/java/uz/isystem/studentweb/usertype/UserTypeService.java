package uz.isystem.studentweb.usertype;

import org.springframework.stereotype.Service;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserTypeService {
    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    // Main functions
    public UserTypeDto get(Integer id){
        UserType userType = getEntity(id);
        UserTypeDto userTypeDto = new UserTypeDto();
        convertEntityToDto(userType, userTypeDto);
        userTypeDto.setId(id);
        return userTypeDto;
    }

    public List<UserType> getByName(String name){
        List<UserType> userTypeList = userTypeRepository.findAllByName(name);
        if(userTypeList.isEmpty()){
            throw new UserNotFoundException("User-Type Not Found !");
        }
        return userTypeList;
    }

    public Boolean create(UserTypeDto userTypeDto){
        UserType userType = convertDtoToEntity(userTypeDto, new UserType());
        userType.setStatus(true);
        userType.setCreatedAt(LocalDateTime.now());
        userTypeRepository.save(userType);
        return true;
    }

    public UserTypeDto update(Integer id,UserTypeDto userTypeDto){
        UserType entity = getEntity(id);
        convertDtoToEntity(userTypeDto, entity);
        entity.setId(id);
        entity.setUpdatedAt(LocalDateTime.now());
        userTypeRepository.save(entity);
        return userTypeDto;
    }

    public void delete(Integer id){
        UserType userType = getEntity(id);
        userType.setDeletedAt(LocalDateTime.now());
        userTypeRepository.save(userType);
    }

    public List<UserTypeDto> getAll() {
        List<UserType> userTypeList = userTypeRepository.findAllByDeletedAtIsNull();
        if(userTypeList.isEmpty()){
            throw new UserNotFoundException("User-Type Not Found !");
        }
        List<UserTypeDto> dtoList = new LinkedList<>();
        for (UserType userType: userTypeList) {
            UserTypeDto userTypeDto = convertEntityToDto(userType, new UserTypeDto());
            dtoList.add(userTypeDto);
        }
        return dtoList;
    }


    // Secondary functions
    public UserType getEntity(Integer id){
        Optional<UserType> optional = userTypeRepository.findById(id);
        if(optional.isEmpty() || optional.get().getDeletedAt() != null){
            throw new UserNotFoundException("User-Type Not Found !");
        }
        return optional.get();
    }

    public UserType convertDtoToEntity(UserTypeDto dto, UserType entity){
        entity.setName(dto.getName());
        entity.setDisplayName(dto.getDisplayName());
        return entity;
    }

    public UserTypeDto convertEntityToDto(UserType entity, UserTypeDto dto){
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDisplayName(entity.getDisplayName());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());

        return dto;
    }

    public UserType getEntityByName(String name) {
        Optional<UserType> optional = userTypeRepository.findByNameAndDeletedAtIsNull(name);
        if (optional.isEmpty()){
            throw new ServerBadRequestException("Usertype not found");
        }
        return optional.get();
    }
}
