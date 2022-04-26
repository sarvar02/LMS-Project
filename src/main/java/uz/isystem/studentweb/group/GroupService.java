package uz.isystem.studentweb.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.studentweb.course.CourseService;
import uz.isystem.studentweb.exception.GroupNotFoundException;
import uz.isystem.studentweb.group.Group;
import uz.isystem.studentweb.group.GroupRepository;
import uz.isystem.studentweb.group.group_type.GroupTypeService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupTypeService groupTypeService;

    public GroupDto get(Integer id){
        Group group = getEntity(id);
//        Course course = courseService.getEntity(group.getCourseId());
//        group.setCourse(course);
        return convertEntityToDto(group, new GroupDto());
    }

    public void create(GroupDto groupDto){

        // Cheking...
        courseService.getEntity(groupDto.getCourseId());
        groupTypeService.getEntity(groupDto.getGroupTypeId());
        Group group = convertDtoToEntity(groupDto, new Group());
        // Setting...
        group.setCourse(courseService.getEntity(group.getCourseId()));
        group.setCreatedAt(LocalDateTime.now());
        group.setStatus(true);
        groupRepository.save(group);
    }

    public List<GroupDto> getAll() {
        List<Group> groupList = groupRepository.findAllByDeletedAtIsNull();
        if(groupList.isEmpty()){
            throw new GroupNotFoundException("Group Not Found !");
        }
        return groupList.stream().map(group -> {
            GroupDto groupDto = new GroupDto();
            convertEntityToDto(group, groupDto);
//            groupDto.setCourseDto(courseService.getOne(groupDto.getCourseId()));
            return groupDto;
        }).collect(Collectors.toList());
    }

    public void delete(Integer id) {
        Group group = getEntity(id);
        group.setDeletedAt(LocalDateTime.now());
        groupRepository.save(group);
    }

    public void update(Integer id, GroupDto groupDto) {
        // Cheking...
        courseService.getEntity(groupDto.getCourseId());

        Group entity = getEntity(id);
        // Convering...
        convertDtoToEntity(groupDto, entity);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(entity.getCreatedAt());

        groupRepository.save(entity);
    }


    //Secondary Functions
    public Group getEntity(Integer id){
        Optional<Group> optional = groupRepository.findById(id);
        if(optional.isEmpty() || optional.get().getDeletedAt() != null){
            throw new GroupNotFoundException("Group Not Found !");
        }
        return optional.get();
    }

    public GroupDto convertEntityToDto(Group entity, GroupDto dto){
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCourseId(entity.getCourseId());
        dto.setCourseDto(courseService.getOne(dto.getCourseId()));
        dto.setGroupTypeId(entity.getGroupTypeId());
        dto.setGroupTypeDto(groupTypeService.get(dto.getGroupTypeId()));
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());

        return dto;
    }

    public Group convertDtoToEntity(GroupDto dto, Group entity){
        entity.setName(dto.getName());
        entity.setCourseId(dto.getCourseId());
        entity.setGroupTypeId(dto.getGroupTypeId());
        return entity;
    }
}
