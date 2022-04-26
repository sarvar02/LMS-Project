package uz.isystem.studentweb.group.group_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.userGroup.UserGroup;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupTypeService {

    @Autowired
    private GroupTypeRepository groupTypeRepository;

    public void create(GroupTypeDto groupTypeDto) {
        GroupType groupType = convertDtoToEntity(groupTypeDto, new GroupType());
        groupType.setCreatedAt(LocalDateTime.now());
        groupType.setStatus(true);

        groupTypeRepository.save(groupType);
    }

    public GroupTypeDto get(Integer id){
        GroupType groupType = getEntity(id);
        return convertEntityToDto(groupType, new GroupTypeDto());
    }

    public void delete(Integer id){
        GroupType groupType = getEntity(id);
        groupType.setDeletedAt(LocalDateTime.now());
        groupTypeRepository.save(groupType);
    }

    public List<GroupTypeDto> getAll(){
        List<GroupType> groupTypeList = groupTypeRepository.findAllByDeletedAtIsNull();
        if(groupTypeList.isEmpty()){
            throw new ServerBadRequestException("There are not any groupy types");
        }
        return
                groupTypeList.stream().
                map(groupType -> convertEntityToDto(groupType,new GroupTypeDto())).
                collect(Collectors.toList());
    }

    public void update(Integer id, GroupTypeDto groupTypeDto){
        GroupType groupType = getEntity(id);
        convertDtoToEntity(groupTypeDto, groupType);
        groupType.setUpdatedAt(LocalDateTime.now());

        groupTypeRepository.save(groupType);
    }


    // Secondary function

    public GroupType getEntity(Integer id){
        Optional<GroupType> optional = groupTypeRepository.findById(id);
        if(optional.isEmpty() || optional.get().getDeletedAt() != null){
            throw new ServerBadRequestException("Group type not found !");
        }
        return optional.get();
    }

    public GroupType convertDtoToEntity(GroupTypeDto groupTypeDto, GroupType groupType){
        groupType.setName(groupTypeDto.getName());
        return groupType;
    }

    public GroupTypeDto convertEntityToDto(GroupType groupType, GroupTypeDto groupTypeDto){
        groupTypeDto.setId(groupType.getId());
        groupTypeDto.setName(groupType.getName());
        groupTypeDto.setStatus(groupType.getStatus());
        groupTypeDto.setCreatedAt(groupType.getCreatedAt());
        groupTypeDto.setDeletedAt(groupType.getDeletedAt());
        groupTypeDto.setUpdatedAt(groupType.getUpdatedAt());

        return groupTypeDto;
    }

}
