package uz.isystem.studentweb.Class;

import org.springframework.stereotype.Service;
import uz.isystem.studentweb.attendence.AttendenceTypeService;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.group.Group;
import uz.isystem.studentweb.room.RoomService;
import uz.isystem.studentweb.userGroup.UserGroup;
import uz.isystem.studentweb.userGroup.UserGroupService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {
    private final ClassRepository classRepository;
    private final AttendenceTypeService attendenceTypeService;
    private final RoomService roomService;
    private final UserGroupService userGroupService;

    public ClassService(ClassRepository classRepository, AttendenceTypeService attendenceTypeService, RoomService roomService, UserGroupService userGroupService) {
        this.classRepository = classRepository;
        this.attendenceTypeService = attendenceTypeService;
        this.roomService = roomService;
        this.userGroupService = userGroupService;
    }

    public ClassDto get(Integer id){
        ClassDto classDto = convertEntityToDto(getEntity(id));
//        classDto.setAttendenceTypeDto(attendenceTypeService.get(classDto.getAttendenceTypeId()));
//        classDto.setRoomDto(roomService.get(classDto.getRoomId()));
//        classDto.setUserGroupDto(userGroupService.get(classDto.getUserGroupId()));
        return classDto;
    }

    public String create(ClassDto classDto){

        // Checking...
        roomService.getEntity(classDto.getRoomId());
        userGroupService.getEntity(classDto.getUserGroupId());
        attendenceTypeService.getEntity(classDto.getAttendenceTypeId());


        Class entity = convertDtoToEntity(classDto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(true);
        entity.setName(generateName(classDto.getDate()));
//        entity.setName(String.format("%s.%s", classDto.getDate().getDayOfMonth(), classDto.getDate().getMonthValue()));

//         Save to DataBase
        classRepository.save(entity);
        return "New class created successfully !";
    }

    public String update(Integer id, ClassDto classDto){
        Class entity = getEntity(id);
        classDto.setUpdatedAt(LocalDateTime.now());
        entity = convertDtoToEntity(classDto);
//        entity.setName(String.format("%s.%s", classDto.getDate().getDayOfMonth(), classDto.getDate().getMonthValue()));
        entity.setName(generateName(classDto.getDate()));
        classRepository.save(entity);
        return "Class successfully updated !";
    }

    public String delete(Integer id){
        Class entity = getEntity(id);
        entity.setDeletedAt(LocalDateTime.now());
        classRepository.save(entity);
        return "Class deleted !";
    }

    public List<ClassDto> getAll(){
        List<Class> classDtoList = classRepository.findAllByDeletedAtIsNull();
        if(classDtoList.isEmpty()){
            throw new ServerBadRequestException("Class not found !");
        }
        return classDtoList
                .stream()
                .map(aClass -> convertEntityToDto(aClass))
                .collect(Collectors.toList());
    }



    // Secondary Functions

    public Class getEntity(Integer id){
        return classRepository.
                findByIdAndDeletedAtIsNull(id).
                orElseThrow(() -> new ServerBadRequestException("Class not found !"));
    }

    public ClassDto convertEntityToDto(Class entity){
        return ClassDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .reasonMessage(entity.getReasonMessage())
                .roomId(entity.getRoomId())
                .userGroupId(entity.getUserGroupId())
                .attendenceTypeId(entity.getAttendenceTypeId())
                .date(entity.getDate())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public Class convertDtoToEntity(ClassDto dto){
        return Class.builder()
                .reasonMessage(dto.getReasonMessage())
                .attendenceTypeId(dto.getAttendenceTypeId())
                .roomId(dto.getRoomId())
                .userGroupId(dto.getUserGroupId())
                .date(dto.getDate())
                .status(dto.getStatus())
                .build();
    }

    public String generateName(LocalDate date){
        return String.format("%s.%s", date.getDayOfMonth(), date.getMonthValue());
    }

    public List<Class> getClassesByGroupdId(Integer groupId){
        List<Class> classList = classRepository.findAllByGroupIdAndDeletedAtIsNull(groupId);
        if(classList.isEmpty()) throw new ServerBadRequestException("CLass not found !");

        return classList
                .stream()
                .filter(aClass -> aClass.getUserGroup().getUser().getUserTypeId() == 2)
                .collect(Collectors.toList());
    }

    public Boolean createLessonForStudents(ClassDto classDto){
        UserGroup userGroup = userGroupService.getEntity(classDto.getUserGroupId());
        Group group = userGroup.getGroup();
        List<UserGroup> userGroupList = userGroupService.getGroupsAndStudentsByGroupId(group.getId());
        List<Class> classList = new LinkedList<>();
        for (UserGroup ug : userGroupList) {
            Class lesson = new Class();
            lesson.setName(generateName(classDto.getDate()));
            lesson = convertDtoToEntity(classDto);
            lesson.setCreatedAt(LocalDateTime.now());
            lesson.setStatus(true);
            lesson.setGroupId(ug.getGroupId());
//            lesson.setUserGroupId(ug.getId());
            classList.add(lesson);
        }
        classRepository.saveAll(classList);
        return true;
    }

}
