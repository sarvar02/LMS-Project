package uz.isystem.studentweb.userGroup;

import org.springframework.stereotype.Service;
import uz.isystem.studentweb.dto.TeacherDetailDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.group.Group;
import uz.isystem.studentweb.group.GroupService;
import uz.isystem.studentweb.user.User;
import uz.isystem.studentweb.user.UserService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserGroupService {
    
    private final UserGroupRepository userGroupRepository;
    private final UserService userService;
    private final GroupService groupService;


    public UserGroupService(UserGroupRepository userGroupRepository, UserService userService, GroupService groupService) {
        this.userGroupRepository = userGroupRepository;
        this.userService = userService;
        this.groupService = groupService;
    }


    // Main Functions

    public UserGroupDto get(Integer id){
        UserGroup userGroup = getEntity(id);
//        userGroup.setUser(userService.get(userGroup.getUserId()));
//        userGroup.setGroup(groupService.get(userGroup.getGroupId()));
        return convertEntityToDto(userGroup, new UserGroupDto());
    }

    public void create(UserGroupDto userGroupDto){
//        Optional<UserGroup> optional = userGroupRepository.findByUserIdAndGroupIdAndDeletedAtIsNull(userGroup.getUserId(), userGroup.getGroupId());
//        if(optional.isPresent() || !groupService.isvalidGroup(userGroup.getGroupId()) || !userService.isValidUser(userGroup.getUserId())){
//            throw new ServerBadRequestException("User not added !");
//        }

        // Cheking...
        userService.getEntity(userGroupDto.getUserId());
        groupService.getEntity(userGroupDto.getGroupId());
        //...

        UserGroup entity = convertDtoToEntity(userGroupDto, new UserGroup());
        entity.setStatus(true);
        entity.setCreatedAt(LocalDateTime.now());
        userGroupRepository.save(entity);
    }

    public List<UserGroupDto> findAll() {
        List<UserGroup> userGroupList = userGroupRepository.findAllByDeletedAtIsNull();
        if(userGroupList.isEmpty()){
            throw new ServerBadRequestException("User group not found !");
        }

        return userGroupList.stream().map(userGroup -> convertEntityToDto(userGroup, new UserGroupDto()))
                .collect(Collectors.toList());
    }

    public void delete(Integer id) {
        UserGroup userGroup = getEntity(id);
        userGroup.setDeletedAt(LocalDateTime.now());
        userGroupRepository.save(userGroup);
    }

    public void update(Integer id, UserGroupDto userGroupDto) {

        // Cheking...
        userService.getEntity(userGroupDto.getUserId());
        groupService.getEntity(userGroupDto.getGroupId());

        UserGroup userGroupEntity = getEntity(id);
        convertDtoToEntity(userGroupDto, userGroupEntity);
        userGroupEntity.setUpdatedAt(LocalDateTime.now());
        userGroupRepository.save(userGroupEntity);
    }



    // Secondary Functions

    public UserGroup getEntity(Integer id){
        Optional<UserGroup> optional = userGroupRepository.findById(id);
        if(optional.isEmpty() || optional.get().getDeletedAt() != null){
            throw new ServerBadRequestException("User Group Not Found !");
        }
        return optional.get();
    }

    public UserGroupDto convertEntityToDto(UserGroup entity, UserGroupDto dto){
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setGroupId(entity.getGroupId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setUser(userService.get(dto.getUserId()));
        dto.setGroup(groupService.get(dto.getGroupId()));

        return dto;
    }

    public UserGroup convertDtoToEntity(UserGroupDto dto, UserGroup entity){
        entity.setUserId(dto.getUserId());
        entity.setGroupId(dto.getGroupId());

        return entity;
    }

    public List<UserGroup> findAllForTeacher(Integer id) {
        List<UserGroup> userGroupList = userGroupRepository.findAllByUserIdAndDeletedAtIsNull(id);
        if (userGroupList.isEmpty()) {
            throw new ServerBadRequestException("User group not found");
        }
        return userGroupList;
    }

    public TeacherDetailDto findStudentsByTeacherId(Integer teacherId) {
        List<UserGroup> userGroupEntityList = userGroupRepository.findAllByUserIdAndDeletedAtIsNull(teacherId);
        TeacherDetailDto teacherDetailDto = new TeacherDetailDto();
        List<Group> groupList = new LinkedList<>();
        for (UserGroup userGroup : userGroupEntityList) {
            groupList.add(userGroup.getGroup());
        }
        teacherDetailDto.setGroupList(groupList);
        List<List<User>> resultList = new LinkedList<>();
        for (Group group : groupList) {
            resultList.add(getStudentsFromGroup(group.getId()));
        }
        teacherDetailDto.setUserList(resultList);
        return teacherDetailDto;
    }

    public List<User> getStudentsFromGroup(Integer groupdId){
        List<UserGroup> userGroupList = getGroupsAndStudentsByGroupId(groupdId);
        List<User> userList = new LinkedList<>();
        for (UserGroup userGroup : userGroupList) {
            if(userGroup.getUser().getUserTypeId() == 2){
                userList.add(userGroup.getUser());
            }
        }
        return userList;
    }

    public List<UserGroup> getGroupsAndStudentsByGroupId(Integer groupId){
        List<UserGroup> userGroupList = userGroupRepository.findAllByGroupIdAndDeletedAtIsNull(groupId);
        if(userGroupList.isEmpty()){
            throw  new ServerBadRequestException("Groupd is empty");
        }
        return userGroupList;
    }

}
